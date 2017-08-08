


-- ----------------------------
-- Table structure for RECEIVESMS_SF
-- ----------------------------
--DROP TABLE "WINNERLOOK"."RECEIVESMS_SF";
CREATE TABLE "WINNERLOOK"."RECEIVESMS_SF" (
"ID" NUMBER(20) NOT NULL ,
"PHONE" VARCHAR2(20 BYTE) NOT NULL ,
"CONTENT" VARCHAR2(1000 BYTE) NULL ,
"SENDTIME" TIMESTAMP(0)  NULL ,
"SYSTIME" TIMESTAMP(0)  NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for SEND_SF
-- ----------------------------
--DROP TABLE "WINNERLOOK"."SEND_SF";
CREATE TABLE "WINNERLOOK"."SEND_SF" (
"ID" NUMBER(20) NOT NULL ,
"PHONE" VARCHAR2(20 BYTE) NOT NULL ,
"CONTENT" VARCHAR2(1000 BYTE) NOT NULL ,
"INTIME" TIMESTAMP(0)  NOT NULL ,
"STATE" NUMBER(11) NOT NULL ,
"SENDTIME" TIMESTAMP(0)  NULL ,
"STATCODE" VARCHAR2(20 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for SEND_SF_DONE
-- ----------------------------
--DROP TABLE "WINNERLOOK"."SEND_SF_DONE";
CREATE TABLE "WINNERLOOK"."SEND_SF_DONE" (
"ID" NUMBER(20) NOT NULL ,
"PHONE" VARCHAR2(20 BYTE) NOT NULL ,
"CONTENT" VARCHAR2(1000 BYTE) NOT NULL ,
"INTIME" TIMESTAMP(0)  NOT NULL ,
"STATE" NUMBER(11) NOT NULL ,
"SENDTIME" TIMESTAMP(0)  NULL ,
"STATCODE" VARCHAR2(20 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for STAT_SF
-- ----------------------------
--DROP TABLE "WINNERLOOK"."STAT_SF";
CREATE TABLE "WINNERLOOK"."STAT_SF" (
"ID" NUMBER(20) NOT NULL ,
"PHONE" VARCHAR2(20 BYTE) NULL ,
"STATCODE" VARCHAR2(20 BYTE) NULL ,
"STATMSG" VARCHAR2(20 BYTE) NULL ,
"SMSID" VARCHAR2(30 BYTE) NULL ,
"SENDTIME" VARCHAR2(34 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Sequence structure for RECEIVESMS_SF_SEQ
-- ----------------------------
--DROP SEQUENCE "WINNERLOOK"."RECEIVESMS_SF_SEQ";
CREATE SEQUENCE "WINNERLOOK"."RECEIVESMS_SF_SEQ"
 INCREMENT BY 1
 MINVALUE 0
 MAXVALUE 9999999999999999999999999999
 START WITH 0
 NOCACHE ;

-- ----------------------------
-- Sequence structure for SEND_SF_DONE_SEQ
-- ----------------------------
--DROP SEQUENCE "WINNERLOOK"."SEND_SF_DONE_SEQ";
CREATE SEQUENCE "WINNERLOOK"."SEND_SF_DONE_SEQ"
 INCREMENT BY 1
 MINVALUE 0
 MAXVALUE 9999999999999999999999999999
 START WITH 0
 NOCACHE ;

-- ----------------------------
-- Sequence structure for SEND_SF_SEQ
-- ----------------------------
--DROP SEQUENCE "WINNERLOOK"."SEND_SF_SEQ";
CREATE SEQUENCE "WINNERLOOK"."SEND_SF_SEQ"
 INCREMENT BY 1
 MINVALUE 0
 MAXVALUE 9999999999999999999999999999
 START WITH 0
 NOCACHE ;

-- ----------------------------
-- Sequence structure for STAT_SF_SEQ
-- ----------------------------
--DROP SEQUENCE "WINNERLOOK"."STAT_SF_SEQ";
CREATE SEQUENCE "WINNERLOOK"."STAT_SF_SEQ"
 INCREMENT BY 1
 MINVALUE 0
 MAXVALUE 9999999999999999999999999999
 START WITH 0
 NOCACHE ;

-- ----------------------------
-- Indexes structure for table RECEIVESMS_SF
-- ----------------------------

-- ----------------------------
-- Checks structure for table RECEIVESMS_SF
-- ----------------------------
ALTER TABLE "WINNERLOOK"."RECEIVESMS_SF" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "WINNERLOOK"."RECEIVESMS_SF" ADD CHECK ("PHONE" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table RECEIVESMS_SF
-- ----------------------------
ALTER TABLE "WINNERLOOK"."RECEIVESMS_SF" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table SEND_SF
-- ----------------------------
CREATE INDEX "WINNERLOOK"."ind_state"
ON "WINNERLOOK"."SEND_SF" ("STATE" ASC)
LOGGING
VISIBLE;

-- ----------------------------
-- Checks structure for table SEND_SF
-- ----------------------------
ALTER TABLE "WINNERLOOK"."SEND_SF" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "WINNERLOOK"."SEND_SF" ADD CHECK ("PHONE" IS NOT NULL);
ALTER TABLE "WINNERLOOK"."SEND_SF" ADD CHECK ("CONTENT" IS NOT NULL);
ALTER TABLE "WINNERLOOK"."SEND_SF" ADD CHECK ("INTIME" IS NOT NULL);
ALTER TABLE "WINNERLOOK"."SEND_SF" ADD CHECK ("STATE" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SEND_SF
-- ----------------------------
ALTER TABLE "WINNERLOOK"."SEND_SF" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table SEND_SF_DONE
-- ----------------------------
CREATE INDEX "WINNERLOOK"."ind_state_done"
ON "WINNERLOOK"."SEND_SF_DONE" ("STATE" ASC)
LOGGING
VISIBLE;

-- ----------------------------
-- Checks structure for table SEND_SF_DONE
-- ----------------------------
ALTER TABLE "WINNERLOOK"."SEND_SF_DONE" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "WINNERLOOK"."SEND_SF_DONE" ADD CHECK ("PHONE" IS NOT NULL);
ALTER TABLE "WINNERLOOK"."SEND_SF_DONE" ADD CHECK ("CONTENT" IS NOT NULL);
ALTER TABLE "WINNERLOOK"."SEND_SF_DONE" ADD CHECK ("INTIME" IS NOT NULL);
ALTER TABLE "WINNERLOOK"."SEND_SF_DONE" ADD CHECK ("STATE" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table SEND_SF_DONE
-- ----------------------------
ALTER TABLE "WINNERLOOK"."SEND_SF_DONE" ADD PRIMARY KEY ("ID");

-- ----------------------------
-- Indexes structure for table STAT_SF
-- ----------------------------

-- ----------------------------
-- Checks structure for table STAT_SF
-- ----------------------------
ALTER TABLE "WINNERLOOK"."STAT_SF" ADD CHECK ("ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table STAT_SF
-- ----------------------------
ALTER TABLE "WINNERLOOK"."STAT_SF" ADD PRIMARY KEY ("ID");
