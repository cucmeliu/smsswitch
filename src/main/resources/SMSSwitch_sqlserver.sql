
USE [smsswitch]
GO
/****** Object:  Table [dbo].[stat_sf]    Script Date: 08/30/2016 19:35:15 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[stat_sf](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[phone] [varchar](20) NULL,
	[statcode] [varchar](20) NULL,
	[statmsg] [varchar](20) NULL,
	[smsid] [varchar](30) NULL,
	[sendtime] [varchar](30) NULL,
 CONSTRAINT [PK_stat_sf] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[send_sf_done]    Script Date: 08/30/2016 19:35:15 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[send_sf_done](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[phone] [varchar](20) NOT NULL,
	[content] [varchar](1000) NOT NULL,
	[intime] [datetime] NOT NULL,
	[state] [int] NOT NULL,
	[sendtime] [datetime] NULL,
	[statcode] [varchar](20) NULL,
 CONSTRAINT [PK_send_sf_done_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[send_sf]    Script Date: 08/30/2016 19:35:15 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[send_sf](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[phone] [varchar](20) NOT NULL,
	[content] [varchar](1000) NOT NULL,
	[intime] [datetime] NOT NULL,
	[state] [int] NOT NULL,
	[sendtime] [datetime] NULL,
	[statcode] [varchar](20) NULL,
 CONSTRAINT [PK_send_sf] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[receivesms_sf]    Script Date: 08/30/2016 19:35:15 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[receivesms_sf](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[phone] [varchar](20) NOT NULL,
	[content] [varchar](1000) NULL,
	[sendtime] [datetime] NULL,
	[systime] [datetime] NULL,
 CONSTRAINT [PK_receivesms_sf] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
