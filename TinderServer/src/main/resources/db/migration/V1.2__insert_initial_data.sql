INSERT INTO profile (id, description, name, sex_type)
VALUES (1, 'Только что кончившая гимназию девица желает выйти замуж за холостого или бездетного'
    ' вдовца с состоянием. Возраста не стесняться',
        'Василиса', 1),
       (2,
        'Красивая с русалочьими глазами, вся сотканная из нервов и оригинальности, зовет на праздник жизни интеллигентного,'
            ' очень богатого господина, способного на сильное яркое чувство; цель — брак',
        'Арсинья', 1),
       (3, 'Вдовец 42 лет желает жениться на девушке „без прошлого“,'
           ' образованной, знающей музыку и обладающей приятным голосом. Теща нежелательна',
        'Безил', 0),
       (4, 'Жену, компаньонку с капиталом в 5 тысяч, ищет солидный господин 35 лет, открывающий столовую. '
           'Дело обещает громадный успех',
        'Антуан', 0),
       (5, 'Кинорежиссер, 26 лет, серьезный, ищу жену-друга, любящую искусство, с капиталом не менее 40 000 руб.'
           ' для собственного производства кинематографических лент. Звание и красота безразлично',
        'Алекс', 0),
       (6,
        'Немедленно шатен, 36 лет, состоящий на государственной службе, жалованья 1700 руб. в год, желает жениться на особе,'
            ' располагающей от 50 до 100 и более тыс. руб. Средства необходимы для раздела выгодного имения.',
        'Роберт', 0),
       (7, '37 лет, из общества (не москвич) с изысканным вкусом желает встретиться с изящной особой,'
           ' действительно из общества (а не интеллигенткой или служащей), эксцентричной, разделяющей его вкусы',
        'Борис', 0),
       (8, 'Солидный офицер, полковник действительной службы желает иметь честь стать мужем жизнерадостной,'
           ' интересной и интеллигентной особы. Я — эстетик и потому некрасивых прошу оставить мою исповедь без внимания',
        'Сергей', 0),
       (9,
        'Адрес для письма желающим со мной сойтись на кратком жизненном пути нашего человеческого организма в рост движения'
            ' беспредельной вечности: Киев, Александровская ул., 5, новому русскому художнику',
        'Ебобо', 0),
       (10,
        'Так жизнь молодая проходит бесследно. А там и скоро конец. Мои девичьи грезы изменили мне. Стремилась'
            ' к семейному очагу, но все рассеялось, как дым. И я одна, я всем чужая. Ищу мужа-друга',
        'Алиса', 1),
       (11, 'Простая баба, но лайки никто не ставит!',
        'Баба', 1);

INSERT INTO profile_likes(from_profile, to_profile)
VALUES (11, 1),(11, 2),(2,11);

INSERT INTO sex_type(profile_id, looking_for) VALUES (1 , 0),
                                               (2 , 0),
                                               (3 , 1),
                                               (4 , 1),
                                               (5 , 1),
                                               (6 , 1),
                                               (7 , 1),
                                               (8 , 1),
                                               (9 , 1),
                                               (10 , 0),
                                               (11 , 1)