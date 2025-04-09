-- AI 기능 등록
INSERT INTO feature (name, default_limit, credit_per_use, unit)
VALUES ('AI 번역', 2000, 10, 'CHARS');

INSERT INTO feature (name, default_limit, credit_per_use, unit)
VALUES ('AI 교정', 2000, 10, 'CHARS');

INSERT INTO feature (name, default_limit, credit_per_use, unit)
VALUES ('AI 뉘앙스 조절', 1500, 20, 'CHARS');

INSERT INTO feature (name, default_limit, credit_per_use, unit)
VALUES ('AI 초안 작성', 200, 50, 'MONTHLY');

-- 요금제 등록
INSERT INTO plan (id, name)
VALUES (1 ,'기본 요금제');

INSERT INTO plan (id, name)
VALUES (2 ,'2000 요금제');

INSERT INTO plan (id, name)
VALUES (3 ,'3000 요금제');

-- 고객 등록
INSERT INTO customer (name, credit_balance, plan_id)
VALUES ('코팡', 5000, 1);

INSERT INTO customer (name, credit_balance, plan_id)
VALUES ('법무법인 단앤장', 10000, 2);

INSERT INTO customer (name, credit_balance, plan_id)
VALUES ('BLDO', 10000, 3);

-- 요금제-기능 연결
INSERT INTO plan_feature (plan_id, feature_id, custom_limit)
VALUES (1, 1, 2000);

INSERT INTO plan_feature (plan_id, feature_id, custom_limit)
VALUES (2, 2, 1000);

INSERT INTO plan_feature (plan_id, feature_id, custom_limit)
VALUES (3, 3, 1500);

INSERT INTO plan_feature (plan_id, feature_id, custom_limit)
VALUES (3, 4, 200);