USE [master]
GO
/****** Object:  Database [medical]    Script Date: 12/20/2020 13:55:05 ******/
CREATE DATABASE [medical] ON  PRIMARY 
( NAME = N'medicalManage', FILENAME = N'D:\Program Files\Microsoft SQL Server\MSSQL10_50.MSSQLSERVER\MSSQL\DATA\medicalManage.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'medicalManage_log', FILENAME = N'D:\Program Files\Microsoft SQL Server\MSSQL10_50.MSSQLSERVER\MSSQL\DATA\medicalManage_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [medical] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [medical].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [medical] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [medical] SET ANSI_NULLS OFF
GO
ALTER DATABASE [medical] SET ANSI_PADDING OFF
GO
ALTER DATABASE [medical] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [medical] SET ARITHABORT OFF
GO
ALTER DATABASE [medical] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [medical] SET AUTO_CREATE_STATISTICS ON
GO
ALTER DATABASE [medical] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [medical] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [medical] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [medical] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [medical] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [medical] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [medical] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [medical] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [medical] SET  DISABLE_BROKER
GO
ALTER DATABASE [medical] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [medical] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [medical] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [medical] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [medical] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [medical] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [medical] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [medical] SET  READ_WRITE
GO
ALTER DATABASE [medical] SET RECOVERY FULL
GO
ALTER DATABASE [medical] SET  MULTI_USER
GO
ALTER DATABASE [medical] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [medical] SET DB_CHAINING OFF
GO
EXEC sys.sp_db_vardecimal_storage_format N'medical', N'ON'
GO
USE [medical]
GO
/****** Object:  Table [dbo].[menu]    Script Date: 12/20/2020 13:55:05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[menu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[title] [nchar](6) NOT NULL,
	[name] [nvarchar](20) NOT NULL,
	[alwaysShow] [nchar](5) NOT NULL,
	[affix] [nchar](5) NULL,
	[icon] [nchar](10) NULL,
	[status] [tinyint] NOT NULL,
	[cache] [tinyint] NULL,
	[pid] [int] NOT NULL,
	[path] [nvarchar](50) NULL,
	[component] [nvarchar](50) NULL,
	[redirect] [nvarchar](20) NULL,
	[sort] [int] NULL,
	[create_time] [datetime] NOT NULL,
	[update_time] [datetime] NULL,
	[delete_time] [datetime] NULL,
 CONSTRAINT [PK_menu] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE UNIQUE NONCLUSTERED INDEX [IX_menu] ON [dbo].[menu] 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[menu] ON
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (1, N'经办人管理 ', N'Users', N'true ', NULL, N'user      ', 1, 0, 0, N'/users', N'layout', N'/users/list', 1, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (2, N'权限管理  ', N'Auth', N'true ', NULL, N'tree      ', 1, 0, 0, N'/auth', N'layout', N'/auth/list', 1, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (3, N'顾客管理  ', N'Client', N'true ', NULL, N'people    ', 1, 0, 0, N'/client', N'layout', N'/client/list', 1, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (4, N'药品管理  ', N'Medicine', N'true ', NULL, N'bug       ', 1, 0, 0, N'/medicine', N'layout', N'/medicine', 1, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (5, N'经办人列表 ', N'ListUsers', N'false', NULL, NULL, 1, 0, 1, N'/userList', N'users/list', NULL, 2, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (6, N'顾客列表  ', N'ListClient', N'false', NULL, NULL, 1, 0, 3, N'/clientList', N'client/list', NULL, 2, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (7, N'药品列表  ', N'ListMedicine', N'false', NULL, NULL, 1, 0, 4, N'/medicineList', N'medicine/list', NULL, 2, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (8, N'菜单列表  ', N'ListMenu', N'false', NULL, NULL, 0, 0, 2, N'/menuList', N'auth/listM', NULL, 2, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (9, N'主页    ', N'HomePage', N'false', NULL, NULL, 1, 0, 0, N'/dashboardIndex', N'layout', N'/dashboard', 0, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (11, N'经办人修改 ', N'EditUsers', N'false', NULL, NULL, 0, 0, 1, N'/userEdit', N'users/list', NULL, 3, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (12, N'经办人创建 ', N'CreateUsers', N'false', NULL, NULL, 1, 0, 1, N'/userCreate', N'users/create', NULL, 4, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (14, N'首页    ', N'Dashboard', N'false', NULL, N'dashboard ', 1, 0, 9, N'/dashboard', N'dashboard/index', NULL, 1, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (17, N'药品添加  ', N'CreateMedicine', N'false', NULL, NULL, 1, 0, 4, N'/medicineCreate', N'medicine/create', NULL, 4, CAST(0x0000A9B400000000 AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (18, N'角色添加  ', N'CreateRole', N'false', NULL, NULL, 1, 0, 2, N'/roleCreate', N'auth/createR', NULL, 4, CAST(0x0000A9B400000000 AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (19, N'顾客添加  ', N'CreateClient', N'false', NULL, NULL, 1, 0, 3, N'/clientCreate', N'client/create', NULL, 3, CAST(0x0000A9B400000000 AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (20, N'购买管理  ', N'Records', N'true ', NULL, N'shopping  ', 1, 0, 0, N'/records', N'layout', N'/records/list', 1, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (22, N'购买列表  ', N'ListRecords', N'false', NULL, NULL, 1, 0, 20, N'/recordsList', N'records/list', NULL, 2, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (23, N'购买添加  ', N'CreateRecords', N'false', NULL, NULL, 1, 0, 20, N'/recordsCreate', N'records/create', NULL, 3, CAST(0x0000AC5C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (25, N'角色列表  ', N'ListRole', N'false', NULL, NULL, 1, 0, 2, N'/roleList', N'auth/listR', NULL, 3, CAST(0x0000AC5E012A96C0 AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (28, N'帮助    ', N'Help', N'false', NULL, NULL, 1, 0, 0, N'/help', N'layout', NULL, 2, CAST(0x0000AC7A00C663A3 AS DateTime), NULL, NULL)
INSERT [dbo].[menu] ([id], [title], [name], [alwaysShow], [affix], [icon], [status], [cache], [pid], [path], [component], [redirect], [sort], [create_time], [update_time], [delete_time]) VALUES (35, N'在线帮助  ', N'HelpIndex', N'false', NULL, N'guide     ', 1, 0, 28, N'/helpIndex', N'help/index', NULL, 2, CAST(0x0000AC7A00C663A3 AS DateTime), NULL, NULL)
SET IDENTITY_INSERT [dbo].[menu] OFF
/****** Object:  Table [dbo].[medicine]    Script Date: 12/20/2020 13:55:05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[medicine](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[mno] [char](12) NOT NULL,
	[mname] [nvarchar](50) NOT NULL,
	[stock] [int] NOT NULL,
	[money] [decimal](10, 2) NULL,
	[mmode] [tinyint] NOT NULL,
	[mefficacy] [nvarchar](50) NOT NULL,
	[create_time] [datetime] NOT NULL,
	[update_time] [datetime] NULL,
	[delete_time] [datetime] NULL,
 CONSTRAINT [PK_medicine_1] PRIMARY KEY CLUSTERED 
(
	[mno] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [IX_medicine] UNIQUE NONCLUSTERED 
(
	[mno] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[medicine] ON
INSERT [dbo].[medicine] ([id], [mno], [mname], [stock], [money], [mmode], [mefficacy], [create_time], [update_time], [delete_time]) VALUES (2, N'20181107    ', N'感冒灵2', 10, CAST(0.00 AS Decimal(10, 2)), 0, N'治感冒', CAST(0x0000AC8C000A7E9E AS DateTime), NULL, NULL)
INSERT [dbo].[medicine] ([id], [mno], [mname], [stock], [money], [mmode], [mefficacy], [create_time], [update_time], [delete_time]) VALUES (1, N'20201105    ', N'感冒灵1', 5, CAST(0.00 AS Decimal(10, 2)), 0, N'治感冒', CAST(0x0000AC8B012060CA AS DateTime), CAST(0x0000AC8C00B29EDC AS DateTime), NULL)
INSERT [dbo].[medicine] ([id], [mno], [mname], [stock], [money], [mmode], [mefficacy], [create_time], [update_time], [delete_time]) VALUES (3, N'20201106    ', N'感冒灵3', 11, CAST(0.00 AS Decimal(10, 2)), 1, N'治感冒', CAST(0x0000AC8C00B2E3C8 AS DateTime), CAST(0x0000AC8400FAAF5E AS DateTime), NULL)
INSERT [dbo].[medicine] ([id], [mno], [mname], [stock], [money], [mmode], [mefficacy], [create_time], [update_time], [delete_time]) VALUES (5, N'20201109    ', N'感冒灵4', 10, CAST(0.00 AS Decimal(10, 2)), 1, N'治感冒', CAST(0x0000AC8D00B2E3C8 AS DateTime), NULL, NULL)
INSERT [dbo].[medicine] ([id], [mno], [mname], [stock], [money], [mmode], [mefficacy], [create_time], [update_time], [delete_time]) VALUES (6, N'20201110    ', N'感冒灵5', 10, CAST(0.00 AS Decimal(10, 2)), 1, N'治感冒', CAST(0x0000AC8E00B2E3C8 AS DateTime), NULL, NULL)
INSERT [dbo].[medicine] ([id], [mno], [mname], [stock], [money], [mmode], [mefficacy], [create_time], [update_time], [delete_time]) VALUES (7, N'20201111    ', N'感冒灵6', 10, CAST(0.00 AS Decimal(10, 2)), 1, N'治感冒', CAST(0x0000AC8F00B2E3C8 AS DateTime), NULL, NULL)
INSERT [dbo].[medicine] ([id], [mno], [mname], [stock], [money], [mmode], [mefficacy], [create_time], [update_time], [delete_time]) VALUES (9, N'20201112    ', N'感冒灵7', 10, CAST(0.00 AS Decimal(10, 2)), 1, N'治感冒', CAST(0x0000AC8A00B2E3C8 AS DateTime), NULL, NULL)
INSERT [dbo].[medicine] ([id], [mno], [mname], [stock], [money], [mmode], [mefficacy], [create_time], [update_time], [delete_time]) VALUES (10, N'20201208    ', N'感冒灵8', 10, NULL, 0, N'治感冒', CAST(0x0000AC8B0102E015 AS DateTime), NULL, NULL)
INSERT [dbo].[medicine] ([id], [mno], [mname], [stock], [money], [mmode], [mefficacy], [create_time], [update_time], [delete_time]) VALUES (11, N'20201209    ', N'感冒灵9', 12, NULL, 0, N'治感冒', CAST(0x0000AC8B01033666 AS DateTime), NULL, NULL)
INSERT [dbo].[medicine] ([id], [mno], [mname], [stock], [money], [mmode], [mefficacy], [create_time], [update_time], [delete_time]) VALUES (12, N'20201210    ', N'感冒灵10', 10, NULL, 0, N'治感冒', CAST(0x0000AC8B01036835 AS DateTime), NULL, NULL)
INSERT [dbo].[medicine] ([id], [mno], [mname], [stock], [money], [mmode], [mefficacy], [create_time], [update_time], [delete_time]) VALUES (13, N'20201212    ', N'感冒灵11', 10, NULL, 1, N'治感冒', CAST(0x0000AC8B01038D10 AS DateTime), NULL, NULL)
SET IDENTITY_INSERT [dbo].[medicine] OFF
/****** Object:  Table [dbo].[client]    Script Date: 12/20/2020 13:55:05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[client](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[cname] [nvarchar](8) NOT NULL,
	[csex] [nchar](1) NULL,
	[cage] [int] NULL,
	[caddress] [nvarchar](50) NULL,
	[cphone] [nvarchar](12) NOT NULL,
	[create_time] [datetime] NOT NULL,
	[update_time] [datetime] NULL,
	[delete_time] [datetime] NULL,
 CONSTRAINT [PK_client] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE UNIQUE NONCLUSTERED INDEX [IX_client] ON [dbo].[client] 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'姓名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'client', @level2type=N'COLUMN',@level2name=N'cname'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'性别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'client', @level2type=N'COLUMN',@level2name=N'csex'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'年龄' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'client', @level2type=N'COLUMN',@level2name=N'cage'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'地址' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'client', @level2type=N'COLUMN',@level2name=N'caddress'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'client', @level2type=N'COLUMN',@level2name=N'cphone'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'录入日期' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'client', @level2type=N'COLUMN',@level2name=N'create_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'顾客表' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'client'
GO
SET IDENTITY_INSERT [dbo].[client] ON
INSERT [dbo].[client] ([id], [cname], [csex], [cage], [caddress], [cphone], [create_time], [update_time], [delete_time]) VALUES (1, N'lye', N'女', 23, N'广东省', N'13640230358', CAST(0x0000AC8F00000000 AS DateTime), CAST(0x0000AC810114D657 AS DateTime), NULL)
INSERT [dbo].[client] ([id], [cname], [csex], [cage], [caddress], [cphone], [create_time], [update_time], [delete_time]) VALUES (2, N'test1', N'男', 18, N'广东广州', N'13640230357', CAST(0x0000A9B000000000 AS DateTime), NULL, NULL)
INSERT [dbo].[client] ([id], [cname], [csex], [cage], [caddress], [cphone], [create_time], [update_time], [delete_time]) VALUES (3, N'test2', N'女', 20, N'广东省肇庆市广东省肇庆市广东省肇庆市', N'13764645465', CAST(0x0000AC8E01514CDC AS DateTime), CAST(0x0000AC6B01517ABA AS DateTime), NULL)
INSERT [dbo].[client] ([id], [cname], [csex], [cage], [caddress], [cphone], [create_time], [update_time], [delete_time]) VALUES (4, N'test3', N'男', 18, N'广东省广东省广东省广东省广东省广东省广东省', N'13423423423', CAST(0x0000AC8F00B5FCF4 AS DateTime), CAST(0x0000AC6C00C4C416 AS DateTime), CAST(0x0000AC8C00B579B6 AS DateTime))
INSERT [dbo].[client] ([id], [cname], [csex], [cage], [caddress], [cphone], [create_time], [update_time], [delete_time]) VALUES (5, N'test4', N'女', 21, N'广东省', N'14231322322', CAST(0x0000AC8C011B56FA AS DateTime), CAST(0x0000AC8C00B5717A AS DateTime), CAST(0x0000AC8C00B579A9 AS DateTime))
INSERT [dbo].[client] ([id], [cname], [csex], [cage], [caddress], [cphone], [create_time], [update_time], [delete_time]) VALUES (6, N'test5', N'女', 18, N'广东省', N'15623245342', CAST(0x0000AC8C00EEE71B AS DateTime), NULL, CAST(0x0000AC8B00A4251D AS DateTime))
SET IDENTITY_INSERT [dbo].[client] OFF
/****** Object:  Table [dbo].[role]    Script Date: 12/20/2020 13:55:05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[role](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role_name] [nchar](10) NOT NULL,
	[mids] [varchar](50) NULL,
	[description] [text] NULL,
	[create_time] [datetime] NOT NULL,
	[update_time] [datetime] NULL,
	[delete_time] [datetime] NULL,
 CONSTRAINT [PK_role] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE UNIQUE NONCLUSTERED INDEX [IX_role] ON [dbo].[role] 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[role] ON
INSERT [dbo].[role] ([id], [role_name], [mids], [description], [create_time], [update_time], [delete_time]) VALUES (1, N'管理员       ', N'*', N'总管理员', CAST(0x0000AC5E012A96C0 AS DateTime), CAST(0x0000AC760119B8F2 AS DateTime), NULL)
INSERT [dbo].[role] ([id], [role_name], [mids], [description], [create_time], [update_time], [delete_time]) VALUES (3, N'购买管理      ', N'(9,14,20,22,23)', N'购买管理账号', CAST(0x0000AC70014377AB AS DateTime), CAST(0x0000AC8C00B4E9F5 AS DateTime), NULL)
INSERT [dbo].[role] ([id], [role_name], [mids], [description], [create_time], [update_time], [delete_time]) VALUES (4, N'经办人管理     ', N'(9,14,1,5,11,12)', N'经办人管理', CAST(0x0000AC81010DA834 AS DateTime), CAST(0x0000AC9101855B5B AS DateTime), NULL)
INSERT [dbo].[role] ([id], [role_name], [mids], [description], [create_time], [update_time], [delete_time]) VALUES (5, N'首页查看      ', N'(9,14)', N'首页查看 ', CAST(0x0000AC81010F96C3 AS DateTime), CAST(0x0000AC8C00B5250C AS DateTime), NULL)
INSERT [dbo].[role] ([id], [role_name], [mids], [description], [create_time], [update_time], [delete_time]) VALUES (6, N'顾客管理      ', N'(9,14,3,6,19)', N'顾客管理员', CAST(0x0000AC8C00B5530E AS DateTime), NULL, NULL)
SET IDENTITY_INSERT [dbo].[role] OFF
/****** Object:  Table [dbo].[users]    Script Date: 12/20/2020 13:55:05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[users](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role_id] [int] NOT NULL,
	[username] [nvarchar](8) NOT NULL,
	[password] [nvarchar](32) NOT NULL,
	[sex] [nchar](1) NULL,
	[phone] [char](12) NOT NULL,
	[avatar] [text] NULL,
	[introduction] [text] NULL,
	[create_time] [datetime] NOT NULL,
	[update_time] [datetime] NULL,
	[delete_time] [datetime] NULL,
 CONSTRAINT [PK_users] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE UNIQUE NONCLUSTERED INDEX [IX_users] ON [dbo].[users] 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'用户名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'users', @level2type=N'COLUMN',@level2name=N'username'
GO
SET IDENTITY_INSERT [dbo].[users] ON
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (1, 1, N'admin', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'女', N'13812832842 ', N'23124211-0476-49e8-a221-df7f96bd77bd.png', N'I am a super administrator!', CAST(0x0000AC8B012060CA AS DateTime), CAST(0x0000AC8C00B3E8D2 AS DateTime), NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (2, 3, N'lye', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'女', N'13812832333 ', N'293ea426-150e-45f7-922d-1fb4d628ab9c.png', N'我是超級管理員', CAST(0x0000AC8B012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (3, 1, N'test', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'男', N'13812832842 ', N'da92225d-64f8-4cf3-8808-d812b9aa2010.png', N'I am a super administrator', CAST(0x0000AC8A012060CA AS DateTime), CAST(0x0000AC9200FBBB4D AS DateTime), NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (4, 1, N'test1', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'男', N'13812832842 ', N'293ea426-150e-45f7-922d-1fb4d628ab9c.png', N'I am a super administrator', CAST(0x0000AC95012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (5, 1, N'test2', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'女', N'13812832833 ', N'293ea426-150e-45f7-922d-1fb4d628ab9c.png', N'I am a super administrator', CAST(0x0000AC8C012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (6, 3, N'test3', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'男', N'13812832842 ', N'293ea426-150e-45f7-922d-1fb4d628ab9c.png', N'I am a super administrator', CAST(0x0000AC8D012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (7, 1, N'test4', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'男', N'13812832842 ', N'293ea426-150e-45f7-922d-1fb4d628ab9c.png', N'I am a super administrator', CAST(0x0000AC89012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (8, 3, N'test5', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'男', N'13812832842 ', N'293ea426-150e-45f7-922d-1fb4d628ab9c.png', N'I am a super administrator', CAST(0x0000AC89012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (9, 1, N'test6', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'男', N'13812832842 ', N'293ea426-150e-45f7-922d-1fb4d628ab9c.png', N'I am a super administrator', CAST(0x0000AC89012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (10, 3, N'test7', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'男', N'13812832842 ', N'293ea426-150e-45f7-922d-1fb4d628ab9c.png', N'I am a super administrator', CAST(0x0000AC88012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (11, 1, N'test8', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'男', N'13812832842 ', N'293ea426-150e-45f7-922d-1fb4d628ab9c.png', N'I am a super administrator', CAST(0x0000AC87012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (12, 3, N'test9', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'男', N'13812832842 ', N'293ea426-150e-45f7-922d-1fb4d628ab9c.png', N'I am a super administrator', CAST(0x0000AC87012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (13, 1, N'test10', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'男', N'13812832841 ', N'293ea426-150e-45f7-922d-1fb4d628ab9c.png', N'I am a super administrator', CAST(0x0000AC85012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (14, 3, N'test11', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'男', N'13812832842 ', N'293ea426-150e-45f7-922d-1fb4d628ab9c.png', N'I am a super administrator', CAST(0x0000AC84012060CA AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (16, 1, N'test12', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'女', N'12342342342 ', NULL, N'test', CAST(0x0000AC8400C663A3 AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (17, 1, N'test1111', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'女', N'15217345342 ', NULL, N'test1111', CAST(0x0000AC85010841BF AS DateTime), NULL, NULL)
INSERT [dbo].[users] ([id], [role_id], [username], [password], [sex], [phone], [avatar], [introduction], [create_time], [update_time], [delete_time]) VALUES (18, 3, N'test123', N'N1NXK0F1cVlhbE44SU5HQ2VJR0R3dz09', N'女', N'13640230358 ', NULL, N'Test', CAST(0x0000AC8C00B415FC AS DateTime), NULL, NULL)
SET IDENTITY_INSERT [dbo].[users] OFF
/****** Object:  Table [dbo].[records]    Script Date: 12/20/2020 13:55:05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[records](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[uid] [int] NOT NULL,
	[cid] [int] NOT NULL,
	[mno] [char](12) NOT NULL,
	[symptom] [nvarchar](50) NULL,
	[number] [int] NULL,
	[remark] [nvarchar](50) NULL,
	[create_time] [datetime] NOT NULL,
	[update_time] [datetime] NULL,
	[delete_time] [datetime] NULL,
 CONSTRAINT [PK_records] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_records] ON [dbo].[records] 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[records] ON
INSERT [dbo].[records] ([id], [uid], [cid], [mno], [symptom], [number], [remark], [create_time], [update_time], [delete_time]) VALUES (1, 1, 1, N'20201105    ', N'感冒', 2, N'This is admin', CAST(0x0000AC6C00B30F47 AS DateTime), CAST(0x0000AC8C00B25E81 AS DateTime), NULL)
INSERT [dbo].[records] ([id], [uid], [cid], [mno], [symptom], [number], [remark], [create_time], [update_time], [delete_time]) VALUES (2, 2, 1, N'20201106    ', N'感冒', 1, N'11', CAST(0x0000AC6C01134AA9 AS DateTime), CAST(0x0000AC6C016F2A2A AS DateTime), NULL)
INSERT [dbo].[records] ([id], [uid], [cid], [mno], [symptom], [number], [remark], [create_time], [update_time], [delete_time]) VALUES (3, 1, 1, N'20201105    ', N'感冒', 1, N'test', CAST(0x0000AC76017848B8 AS DateTime), NULL, CAST(0x0000AC94017B3788 AS DateTime))
INSERT [dbo].[records] ([id], [uid], [cid], [mno], [symptom], [number], [remark], [create_time], [update_time], [delete_time]) VALUES (4, 1, 1, N'20201105    ', N'', 1, N'', CAST(0x0000AC810098067F AS DateTime), NULL, CAST(0x0000AC8C00AF211D AS DateTime))
INSERT [dbo].[records] ([id], [uid], [cid], [mno], [symptom], [number], [remark], [create_time], [update_time], [delete_time]) VALUES (5, 1, 1, N'20201105    ', N'', 2, N'', CAST(0x0000AC81009EDECF AS DateTime), NULL, CAST(0x0000AC8C00AF2131 AS DateTime))
INSERT [dbo].[records] ([id], [uid], [cid], [mno], [symptom], [number], [remark], [create_time], [update_time], [delete_time]) VALUES (6, 1, 1, N'20201105    ', N'', 2, N'', CAST(0x0000AC8C00B29EDC AS DateTime), NULL, CAST(0x0000AC910187614C AS DateTime))
SET IDENTITY_INSERT [dbo].[records] OFF
/****** Object:  Default [DF_menu_alwaysShow]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[menu] ADD  CONSTRAINT [DF_menu_alwaysShow]  DEFAULT (N'true') FOR [alwaysShow]
GO
/****** Object:  Default [DF_menu_status]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[menu] ADD  CONSTRAINT [DF_menu_status]  DEFAULT ((1)) FOR [status]
GO
/****** Object:  Default [DF_menu_cache]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[menu] ADD  CONSTRAINT [DF_menu_cache]  DEFAULT ((0)) FOR [cache]
GO
/****** Object:  Default [DF_menu_parent_id]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[menu] ADD  CONSTRAINT [DF_menu_parent_id]  DEFAULT ((0)) FOR [pid]
GO
/****** Object:  Default [DF_menu_sort]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[menu] ADD  CONSTRAINT [DF_menu_sort]  DEFAULT ((1)) FOR [sort]
GO
/****** Object:  Default [DF_medicine_stock]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[medicine] ADD  CONSTRAINT [DF_medicine_stock]  DEFAULT ((0)) FOR [stock]
GO
/****** Object:  Default [DF_medicine_money]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[medicine] ADD  CONSTRAINT [DF_medicine_money]  DEFAULT ((0)) FOR [money]
GO
/****** Object:  Default [DF_Table_1_nmode]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[medicine] ADD  CONSTRAINT [DF_Table_1_nmode]  DEFAULT ((1)) FOR [mmode]
GO
/****** Object:  Default [DF_users_role_id]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF_users_role_id]  DEFAULT ((1)) FOR [role_id]
GO
/****** Object:  Default [DF_users_sex]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF_users_sex]  DEFAULT ('男') FOR [sex]
GO
/****** Object:  Default [DF_records_number]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[records] ADD  CONSTRAINT [DF_records_number]  DEFAULT ((1)) FOR [number]
GO
/****** Object:  ForeignKey [FK_users_role]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[users]  WITH CHECK ADD  CONSTRAINT [FK_users_role] FOREIGN KEY([role_id])
REFERENCES [dbo].[role] ([id])
GO
ALTER TABLE [dbo].[users] CHECK CONSTRAINT [FK_users_role]
GO
/****** Object:  ForeignKey [FK_records_client]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[records]  WITH CHECK ADD  CONSTRAINT [FK_records_client] FOREIGN KEY([cid])
REFERENCES [dbo].[client] ([id])
GO
ALTER TABLE [dbo].[records] CHECK CONSTRAINT [FK_records_client]
GO
/****** Object:  ForeignKey [FK_records_medicine]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[records]  WITH CHECK ADD  CONSTRAINT [FK_records_medicine] FOREIGN KEY([mno])
REFERENCES [dbo].[medicine] ([mno])
GO
ALTER TABLE [dbo].[records] CHECK CONSTRAINT [FK_records_medicine]
GO
/****** Object:  ForeignKey [FK_records_users]    Script Date: 12/20/2020 13:55:05 ******/
ALTER TABLE [dbo].[records]  WITH CHECK ADD  CONSTRAINT [FK_records_users] FOREIGN KEY([uid])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[records] CHECK CONSTRAINT [FK_records_users]
GO
