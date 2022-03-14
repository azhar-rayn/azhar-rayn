package pk.org.rayn.zindagi.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pk.org.rayn.zindagi.daos.ScripturesDao;
import pk.org.rayn.zindagi.domain.ScriptureLanguageDTO;
import pk.org.rayn.zindagi.domain.ScripturesObject;
import pk.org.rayn.zindagi.domain.TopicVerseContentFragments;
import pk.org.rayn.zindagi.domain.VerseObject;
import pk.org.rayn.zindagi.domain.VerseObject.VerseContent;
import pk.org.rayn.zindagi.domain.VerseObject.VerseContent.Fragment;

@Service
public class ScripturesService {
	@Autowired
	ScripturesDao scripturesDao;

	public Collection<ScripturesObject> listScriptures() {
		var scripturesAndLanguages = scripturesDao.listScriptures();
		Map<UUID, ScripturesObject> res = new HashMap<>();
		for (ScriptureLanguageDTO sl : scripturesAndLanguages) {
			var scripture = res.computeIfAbsent(sl.scriptureId(), k -> new ScripturesObject(sl.scriptureId(),
					sl.scriptureName(), sl.author(), sl.publishDate(), new ArrayList<>()));
			scripture.languages().add(new ScripturesObject.Language(sl.languageId(), sl.languageName(), sl.language(),
					sl.translator(), sl.nativeLanguage()));
		}

		return res.values();
	}

	public Collection<VerseObject> getVerses(UUID scriptureId, List<UUID> languages, Integer page, Integer pageLength) {
		return convertToVerseObject(scripturesDao.getVerses(scriptureId, languages, page, pageLength));
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
