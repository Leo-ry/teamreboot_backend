-- 고객관리 테이블
CREATE TABLE customer (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    credit_balance BIGINT NOT NULL DEFAULT 0,
    plan_id BIGINT NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    -- updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

-- 요금제
CREATE TABLE plan (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    -- updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

-- 고객의 요금제 및 기능
CREATE TABLE plan_feature (
    id BIGINT NOT NULL AUTO_INCREMENT,
    plan_id BIGINT NOT NULL,
    feature_id BIGINT NOT NULL,
    custom_limit BIGINT NOT NULL,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    -- updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

-- AI 기능 내역
CREATE TABLE feature (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    default_limit BIGINT,
    credit_per_use BIGINT,
    unit VARCHAR(50), -- MONTHLY, CHARS, ETC
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    -- updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

-- 크레딧 사용량
CREATE TABLE credit_usage (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    feature_id BIGINT NOT NULL,
    used_at timestamp,
    unit_used BIGINT,
    credit_used BIGINT,
    status VARCHAR(50),
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    -- updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

-- 크레딧 입출금
CREATE TABLE credit_transaction (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    amount BIGINT,
    type VARCHAR(50),
    related_usage_id BIGINT,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    -- updated_at timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);