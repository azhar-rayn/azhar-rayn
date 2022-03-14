package pk.org.rayn.zindagi.services;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pk.org.rayn.zindagi.daos.TopicsDao;
import pk.org.rayn.zindagi.domain.Topic;
import pk.org.rayn.zindagi.domain.TopicGroupedVerseContentFragments;
import pk.org.rayn.zindagi.domain.TopicVerseContentFragments;
import pk.org.rayn.zindagi.domain.VerseGroupMember;
import pk.org.rayn.zindagi.domain.GroupedVerseObject;
import pk.org.rayn.zindagi.domain.VerseObject;
import pk.org.rayn.zindagi.domain.VerseObject.VerseContent;
import pk.org.rayn.zindagi.domain.VerseObject.VerseContent.Fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

@Service
public class TopicsService {
	@Autowired
	TopicsDao topicsDao;

	public List<Topic> listTopics(UUID scriptureId) {
		return topicsDao.listTopics(scriptureId);
	}


	public List<Topic> getRelatedTopics(UUID id) {
		return topicsDao.getRelatedTopics(id);
	}

	public Collection<GroupedVerseObject> getVerseGroups(UUID id, List<UUID> languages, List<UUID> scriptures) {
		return convertToGroupedVerseObject(topicsDao.getVerseGroups(id, languages, scriptures));
	}

	public static Collection<GroupedVerseObject> convertToGroupedVerseObject(
			List<TopicGroupedVerseContentFragments> groupedVersesAndFragments) {
		Map<UUID, GroupedVerseObject> groupedVerses = new LinkedHashMap<>();
		for (TopicGroupedVerseContentFragments tgvcf : groupedVersesAndFragments) {
			GroupedVerseObject gvo = groupedVerses.computeIfAbsent(tgvcf.verseGroupId(),
					k -> new GroupedVerseObject(tgvcf.verseGroupId(), tgvcf.groupMetadata(), new ArrayList<>()));

			VerseGroupMember vgm;

			try {
				vgm = gvo.verseGroupMembers().stream().filter(f -> f.vo().id().equals(tgvcf.tvcf().id())).findFirst().get();
			} catch (NoSuchElementException | NullPointerException e) {
				vgm = new VerseGroupMember(new VerseObject(tgvcf.tvcf().id(), tgvcf.tvcf().scriptureId(),
						tgvcf.tvcf().verseOrder(), tgvcf.tvcf().metadata(), new LinkedHashMap<>()), tgvcf.isPrimary());
				gvo.verseGroupMembers().add(vgm);
			}

			VerseContent vc = vgm.vo().languages().computeIfAbsent(tgvcf.tvcf().languageId(),
					k -> new VerseContent(tgvcf.tvcf().content(), new ArrayList<>()));
			if (tgvcf.tvcf().fragmentType() != null) {
				vc.fragments().add(
						new Fragment(tgvcf.tvcf().fragmentType(), tgvcf.tvcf().fragmentBeginning(), tgvcf.tvcf().fragmentEnding()));
			}

		}

		return groupedVerses.values();
	}

	public Collection<VerseObject> getVerses(UUID id, List<UUID> languages) {
		return convertToVerseObject(topicsDao.getVerses(id, languages));
	}

	public static Collection<VerseObject> convertToVerseObject(List<TopicVerseContentFragments> versesAndFragments) {
		Map<UUID, VerseObject> verses = new LinkedHashMap<>();
		for (TopicVerseContentFragments tvcf : versesAndFragments) {
			VerseObject vo = verses.computeIfAbsent(tvcf.id(), k -> new VerseObject(tvcf.id(), tvcf.scriptureId(),
					tvcf.verseOrder(), tvcf.metadata(), new LinkedHashMap<>()));
			VerseContent vc = vo.languages().computeIfAbsent(tvcf.languageId(),
					k -> new VerseContent(tvcf.content(), new ArrayList<>()));
			if (tvcf.fragmentType() != null) {
				vc.fragments().add(new Fragment(tvcf.fragmentType(), tvcf.fragmentBeginning(), tvcf.fragmentEnding()));
			}
		}

		return verses.values();
	}

}
