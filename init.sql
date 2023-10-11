-- "outbox-poc" ad?nda bir PostgreSQL veritaban? olu?turun
CREATE DATABASE outbox_poc;

-- Olu?turulan veritaban?na ba?lan?n
\c outbox_poc;

-- "accounts" ad?nda bir tablo olu?turun
CREATE TABLE "public"."accounts"
(
    "id"           uuid NOT NULL,
    "username"     character varying(255),
    "mail"         character varying(255),
    "password"     character varying(255),
    "mailstatus"   character varying(255),
    "created_date" timestamp,
    CONSTRAINT "accounts_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

-- "outboxs" ad?nda bir tablo olu?turun
CREATE TABLE "public"."outboxs"
(
    "id"            uuid NOT NULL,
    "type"          character varying(255),
    "payload"       character varying(2000),
    CONSTRAINT "outboxs_pkey" PRIMARY KEY ("id")
) WITH (oids = false);
