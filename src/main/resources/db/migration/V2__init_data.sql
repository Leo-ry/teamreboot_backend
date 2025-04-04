INSERT INTO feature (name, default_limit, credit_per_use, unit) VALUES (
    ('AI 번역', 2000, 10, 'CHARS'),
    ('AI 교정', 2000, 10, 'CHARS'),
    ('AI 뉘앙스 조절', 1500, 20, 'CHARS'),
    ('AI 초안 작성', 200, 50, 'MONTHLY'),
);

INSERT INTO plan (name) VALUES (
    ('기본 요금제'),
);

INSERT INTO customer (name, credit_balance, plan_id) VALUES (
    ('코팡', 5000, 1),
    ('법무법인 단앤장', 10000, 1),
    ('WW', 10000, 1),
);

INSERT INTO plan_feature (plan_id, feature_id, custom_limit) VALUES (
    (1, 1, 2000),
    (1, 2, 1000),
    (1, 3, 1500),
    (1, 4, 200),
);