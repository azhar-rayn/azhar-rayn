WITH surahs(num, ara, eng, trans) as ( VALUES
(1, 'سورة الفاتحة', 'Al-Fatiha', 'The Opening'),
(2, 'سورة البقرة', 'Al-Baqara', 'The Cow'),
(3, 'سورة آل عمران', 'Aal-e-Imran', 'The family of Imran'),
(4, 'سورة النساء', 'An-Nisa', 'The Women'),
(5, 'سورة المائدة', 'Al-Maeda', 'The Table Spread'),
(6, 'سورة الأنعام', 'Al-Anaam', 'The cattle'),
(7, 'سورة الأعراف', 'Al-Araf', 'The heights'),
(8, 'سورة الأنفال', 'Al-Anfal', 'Spoils of war, booty'),
(9, 'سورة التوبة', 'At-Taubah', 'Repentance'),
(10, 'سورة يونس', 'Yunus', 'Jonah'),
(11, 'سورة هود', 'Hud', 'Hud'),
(12, 'سورة يوسف', 'Yusuf', 'Joseph'),
(13, 'سورة الرعد', 'Ar-Rad', 'The Thunder'),
(14, 'سورة إبراهيم', 'Ibrahim', 'Abraham'),
(15, 'سورة الحجر', 'Al-Hijr', 'Stoneland, Rock city, Al-Hijr valley'),
(16, 'سورة النحل', 'An-Nahl', 'The Bee'),
(17, 'سورة الإسراء', 'Al-Isra', 'The night journey'),
(18, 'سورة الكهف', 'Al-Kahf', 'The cave'),
(19, 'سورة مريم', 'Maryam', 'Mary'),
(20, 'سورة طه', 'Taha', 'Taha'),
(21, 'سورة الأنبياء', 'Al-Anbiya', 'The Prophets'),
(22, 'سورة الحج', 'Al-Hajj', 'The Pilgrimage'),
(23, 'سورة المؤمنون', 'Al-Mumenoon', 'The Believers'),
(24, 'سورة النور', 'An-Noor', 'The Light'),
(25, 'سورة الفرقان', 'Al-Furqan', 'The Standard'),
(26, 'سورة الشعراء', 'Ash-Shuara', 'The Poets'),
(27, 'سورة النمل', 'An-Naml', 'THE ANT'),
(28, 'سورة القصص', 'Al-Qasas', 'The Story'),
(29, 'سورة العنكبوت', 'Al-Ankaboot', 'The Spider'),
(30, 'سورة الروم', 'Ar-Room', 'The Romans'),
(31, 'سورة لقمان', 'Luqman', 'Luqman'),
(32, 'سورة السجدة', 'As-Sajda', 'The Prostration'),
(33, 'سورة الأحزاب', 'Al-Ahzab', 'The Coalition'),
(34, 'سورة سبأ', 'Saba', 'Saba'),
(35, 'سورة فاطر', 'Fatir', 'Originator'),
(36, 'سورة يس', 'Ya Seen', 'Ya Seen'),
(37, 'سورة الصافات', 'As-Saaffat', 'Those who set the ranks'),
(38, 'سورة ص', 'Sad', 'Sad'),
(39, 'سورة الزمر', 'Az-Zumar', 'The Troops'),
(40, 'سورة غافر', 'Ghafir', 'The Forgiver'),
(41, 'سورة فصلت', 'Fussilat', 'Explained in detail'),
(42, 'سورة الشورى', 'Ash-Shura', 'Council, Consultation'),
(43, 'سورة الزخرف', 'Az-Zukhruf', 'Ornaments of Gold'),
(44, 'سورة الدخان', 'Ad-Dukhan', 'The Smoke'),
(45, 'سورة الجاثية', 'Al-Jathiya', 'Crouching'),
(46, 'سورة الأحقاف', 'Al-Ahqaf', 'The wind-curved sandhills'),
(47, 'سورة محمد', 'Muhammad', 'Muhammad'),
(48, 'سورة الفتح', 'Al-Fath', 'The victory'),
(49, 'سورة الحجرات', 'Al-Hujraat', 'The private apartments'),
(50, 'سورة ق', 'Qaf', 'Qaf'),
(51, 'سورة الذاريات', 'Adh-Dhariyat', 'The winnowing winds'),
(52, 'سورة الطور', 'At-tur', 'Mount Sinai'),
(53, 'سورة النجم', 'An-Najm', 'The Star'),
(54, 'سورة القمر', 'Al-Qamar', 'The moon'),
(55, 'سورة الرحمن', 'Al-Rahman', 'The Beneficient'),
(56, 'سورة الواقعة', 'Al-Waqia', 'The Event, The Inevitable'),
(57, 'سورة الحديد', 'Al-Hadid', 'The Iron'),
(58, 'سورة المجادلة', 'Al-Mujadila', 'She that disputes'),
(59, 'سورة الحشر', 'Al-Hashr', 'Exile'),
(60, 'سورة الممتحنة', 'Al-Mumtahina', 'She that is to be examined'),
(61, 'سورة الصف', 'As-Saff', 'The Ranks'),
(62, 'سورة الجمعة', 'Al-Jumua', 'The congregation, Friday'),
(63, 'سورة المنافقون', 'Al-Munafiqoon', 'The Hypocrites'),
(64, 'سورة التغابن', 'At-Taghabun', 'Mutual Disillusion'),
(65, 'سورة الطلاق', 'At-Talaq', 'Divorce'),
(66, 'سورة التحريم', 'At-Tahrim', 'Banning'),
(67, 'سورة الملك', 'Al-Mulk', 'The Sovereignty'),
(68, 'سورة القلم', 'Al-Qalam', 'The Pen'),
(69, 'سورة الحاقة', 'Al-Haaqqa', 'The reality'),
(70, 'سورة المعارج', 'Al-Maarij', 'The Ascending stairways'),
(71, 'سورة نوح', 'Nooh', 'Nooh'),
(72, 'سورة الجن', 'Al-Jinn', 'The Jinn'),
(73, 'سورة المزمل', 'Al-Muzzammil', 'The enshrouded one'),
(74, 'سورة المدثر', 'Al-Muddathir', 'The cloaked one'),
(75, 'سورة القيامة', 'Al-Qiyama', 'The rising of the dead'),
(76, 'سورة الإنسان', 'Al-Insan', 'The man'),
(77, 'سورة المرسلات', 'Al-Mursalat', 'The emissaries'),
(78, 'سورة النبأ', 'An-Naba', 'The tidings'),
(79, 'سورة النازعات', 'An-Naziat', 'Those who drag forth'),
(80, 'سورة عبس', 'Abasa', 'He Frowned'),
(81, 'سورة التكوير', 'At-Takwir', 'The Overthrowing'),
(82, 'سورة الإنفطار', 'AL-Infitar', 'The Cleaving'),
(83, 'سورة المطففين', 'Al-Mutaffifin', 'Defrauding'),
(84, 'سورة الانشقاق', 'Al-Inshiqaq', 'The Sundering, Splitting Open'),
(85, 'سورة البروج', 'Al-Burooj', 'The Mansions of the stars'),
(86, 'سورة الطارق', 'At-Tariq', 'The morning star'),
(87, 'سورة الأعلى', 'Al-Ala', 'The Most High'),
(88, 'سورة الغاشية', 'Al-Ghashiya', 'The Overwhelming'),
(89, 'سورة الفجر', 'Al-Fajr', 'The Dawn'),
(90, 'سورة البلد', 'Al-Balad', 'The City'),
(91, 'سورة الشمس', 'Ash-Shams', 'The Sun'),
(92, 'سورة الليل', 'Al-Lail', 'The night'),
(93, 'سورة الضحى', 'Ad-Dhuha', 'The morning hours'),
(94, 'سورة الشرح', 'Al-Inshirah', 'Solace'),
(95, 'سورة التين', 'At-Tin', 'The Fig'),
(96, 'سورة العلق', 'Al-Alaq', 'The Clot'),
(97, 'سورة القدر', 'Al-Qadr', 'The Power'),
(98, 'سورة البينة', 'Al-Bayyina', 'The Clear proof'),
(99, 'سورة الزلزلة', 'Al-Zalzala', 'The earthquake'),
(100, 'سورة العاديات', 'Al-Adiyat', 'The Chargers'),
(101, 'سورة القارعة', 'Al-Qaria', 'The Calamity'),
(102, 'سورة التكاثر', 'At-Takathur', 'Competition'),
(103, 'سورة العصر', 'Al-Asr', 'The declining day'),
(104, 'سورة الهمزة', 'Al-Humaza', 'The Traducer'),
(105, 'سورة الفيل', 'Al-fil', 'The Elephant'),
(106, 'سورة قريش', 'Quraish', 'Quraish'),
(107, 'سورة الماعون', 'Al-Maun', 'Alms Giving'),
(108, 'سورة الكوثر', 'Al-Kauther', 'Abundance'),
(109, 'سورة الكافرون', 'Al-Kafiroon', 'The Disbelievers'),
(110, 'سورة النصر', 'An-Nasr', 'The Succour'),
(111, 'سورة المسد', 'Al-Masadd', 'The Flame'),
(112, 'سورة الإخلاص', 'Al-Ikhlas', 'Absoluteness'),
(113, 'سورة الفلق', 'Al-Falaq', 'The day break'),
(114, 'سورة الناس', 'An-Nas', 'The mankind'))

update verses v set metadata = metadata || jsonb_build_object('suraNameArabic', ara, 'suraNameEnglish', eng, 'suraNameTranslation', trans) from surahs where cast (v.metadata->>'sura' as int) = surahs.num;
