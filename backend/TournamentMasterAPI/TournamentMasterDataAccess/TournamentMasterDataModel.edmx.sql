
-- --------------------------------------------------
-- Entity Designer DDL Script for SQL Server 2005, 2008, 2012 and Azure
-- --------------------------------------------------
-- Date Created: 10/02/2017 16:03:11
-- Generated from EDMX file: C:\Users\Curtis Barlow-Wilkes\documents\visual studio 2015\Projects\TournamentMasterAPI\TournamentMasterDataAccess\TournamentMasterDataModel.edmx
-- --------------------------------------------------

SET QUOTED_IDENTIFIER OFF;
GO
USE [TournamentMasterDB];
GO
IF SCHEMA_ID(N'dbo') IS NULL EXECUTE(N'CREATE SCHEMA [dbo]');
GO

-- --------------------------------------------------
-- Dropping existing FOREIGN KEY constraints
-- --------------------------------------------------

IF OBJECT_ID(N'[dbo].[FK_AccountOrganization_Account]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[AccountOrganization] DROP CONSTRAINT [FK_AccountOrganization_Account];
GO
IF OBJECT_ID(N'[dbo].[FK_AccountOrganization_Organization]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[AccountOrganization] DROP CONSTRAINT [FK_AccountOrganization_Organization];
GO
IF OBJECT_ID(N'[dbo].[FK_CompetitorTournament_Competitor]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[CompetitorTournament] DROP CONSTRAINT [FK_CompetitorTournament_Competitor];
GO
IF OBJECT_ID(N'[dbo].[FK_CompetitorTournament_Tournament]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[CompetitorTournament] DROP CONSTRAINT [FK_CompetitorTournament_Tournament];
GO
IF OBJECT_ID(N'[dbo].[FK_TournamentRound]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Rounds] DROP CONSTRAINT [FK_TournamentRound];
GO
IF OBJECT_ID(N'[dbo].[FK_RoundPairing]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Pairings] DROP CONSTRAINT [FK_RoundPairing];
GO
IF OBJECT_ID(N'[dbo].[FK_OrganizationTournament]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Tournaments] DROP CONSTRAINT [FK_OrganizationTournament];
GO
IF OBJECT_ID(N'[dbo].[FK_OrganizationCompetitor]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Competitors] DROP CONSTRAINT [FK_OrganizationCompetitor];
GO
IF OBJECT_ID(N'[dbo].[FK_PairingCompetitor]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Pairings] DROP CONSTRAINT [FK_PairingCompetitor];
GO
IF OBJECT_ID(N'[dbo].[FK_PairingCompetitor2]', 'F') IS NOT NULL
    ALTER TABLE [dbo].[Pairings] DROP CONSTRAINT [FK_PairingCompetitor2];
GO

-- --------------------------------------------------
-- Dropping existing tables
-- --------------------------------------------------

IF OBJECT_ID(N'[dbo].[Accounts]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Accounts];
GO
IF OBJECT_ID(N'[dbo].[Organizations]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Organizations];
GO
IF OBJECT_ID(N'[dbo].[Competitors]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Competitors];
GO
IF OBJECT_ID(N'[dbo].[Tournaments]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Tournaments];
GO
IF OBJECT_ID(N'[dbo].[Rounds]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Rounds];
GO
IF OBJECT_ID(N'[dbo].[Pairings]', 'U') IS NOT NULL
    DROP TABLE [dbo].[Pairings];
GO
IF OBJECT_ID(N'[dbo].[AccountOrganization]', 'U') IS NOT NULL
    DROP TABLE [dbo].[AccountOrganization];
GO
IF OBJECT_ID(N'[dbo].[CompetitorTournament]', 'U') IS NOT NULL
    DROP TABLE [dbo].[CompetitorTournament];
GO

-- --------------------------------------------------
-- Creating all tables
-- --------------------------------------------------

-- Creating table 'Accounts'
CREATE TABLE [dbo].[Accounts] (
    [Id] int IDENTITY(1,1) NOT NULL,
    [Email] nvarchar(50)  NOT NULL,
    [FirstName] nvarchar(50)  NOT NULL,
    [LastName] nvarchar(50)  NOT NULL,
    [Password] nvarchar(50)  NOT NULL
);
GO

-- Creating table 'Organizations'
CREATE TABLE [dbo].[Organizations] (
    [Id] int IDENTITY(1,1) NOT NULL,
    [Name] nvarchar(50)  NOT NULL
);
GO

-- Creating table 'Competitors'
CREATE TABLE [dbo].[Competitors] (
    [Id] int IDENTITY(1,1) NOT NULL,
    [FirstName] nvarchar(50)  NOT NULL,
    [LastName] nvarchar(50)  NOT NULL,
    [Email] nvarchar(50)  NOT NULL,
    [Rating] int  NOT NULL,
    [OrganizationId] int  NOT NULL,
    [Gender] nvarchar(max)  NOT NULL
);
GO

-- Creating table 'Tournaments'
CREATE TABLE [dbo].[Tournaments] (
    [Id] int IDENTITY(1,1) NOT NULL,
    [OrganizationId] int  NOT NULL,
    [StartDate] datetime  NOT NULL,
    [Format] int  NOT NULL,
    [OnGoing] bit  NOT NULL
);
GO

-- Creating table 'Rounds'
CREATE TABLE [dbo].[Rounds] (
    [Id] int IDENTITY(1,1) NOT NULL,
    [TournamentId] int  NOT NULL,
    [RoundNumber] int  NOT NULL
);
GO

-- Creating table 'Pairings'
CREATE TABLE [dbo].[Pairings] (
    [Id] int IDENTITY(1,1) NOT NULL,
    [RoundId] int  NOT NULL,
    [Result] int  NOT NULL,
    [CompetitorId1] int  NOT NULL,
    [CompetitorId2] int  NOT NULL
);
GO

-- Creating table 'AccountOrganization'
CREATE TABLE [dbo].[AccountOrganization] (
    [Accounts_Id] int  NOT NULL,
    [Organizations_Id] int  NOT NULL
);
GO

-- Creating table 'CompetitorTournament'
CREATE TABLE [dbo].[CompetitorTournament] (
    [Competitors_Id] int  NOT NULL,
    [Tournaments_Id] int  NOT NULL
);
GO

-- --------------------------------------------------
-- Creating all PRIMARY KEY constraints
-- --------------------------------------------------

-- Creating primary key on [Id] in table 'Accounts'
ALTER TABLE [dbo].[Accounts]
ADD CONSTRAINT [PK_Accounts]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- Creating primary key on [Id] in table 'Organizations'
ALTER TABLE [dbo].[Organizations]
ADD CONSTRAINT [PK_Organizations]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- Creating primary key on [Id] in table 'Competitors'
ALTER TABLE [dbo].[Competitors]
ADD CONSTRAINT [PK_Competitors]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- Creating primary key on [Id] in table 'Tournaments'
ALTER TABLE [dbo].[Tournaments]
ADD CONSTRAINT [PK_Tournaments]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- Creating primary key on [Id] in table 'Rounds'
ALTER TABLE [dbo].[Rounds]
ADD CONSTRAINT [PK_Rounds]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- Creating primary key on [Id] in table 'Pairings'
ALTER TABLE [dbo].[Pairings]
ADD CONSTRAINT [PK_Pairings]
    PRIMARY KEY CLUSTERED ([Id] ASC);
GO

-- Creating primary key on [Accounts_Id], [Organizations_Id] in table 'AccountOrganization'
ALTER TABLE [dbo].[AccountOrganization]
ADD CONSTRAINT [PK_AccountOrganization]
    PRIMARY KEY CLUSTERED ([Accounts_Id], [Organizations_Id] ASC);
GO

-- Creating primary key on [Competitors_Id], [Tournaments_Id] in table 'CompetitorTournament'
ALTER TABLE [dbo].[CompetitorTournament]
ADD CONSTRAINT [PK_CompetitorTournament]
    PRIMARY KEY CLUSTERED ([Competitors_Id], [Tournaments_Id] ASC);
GO

-- --------------------------------------------------
-- Creating all FOREIGN KEY constraints
-- --------------------------------------------------

-- Creating foreign key on [Accounts_Id] in table 'AccountOrganization'
ALTER TABLE [dbo].[AccountOrganization]
ADD CONSTRAINT [FK_AccountOrganization_Account]
    FOREIGN KEY ([Accounts_Id])
    REFERENCES [dbo].[Accounts]
        ([Id])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating foreign key on [Organizations_Id] in table 'AccountOrganization'
ALTER TABLE [dbo].[AccountOrganization]
ADD CONSTRAINT [FK_AccountOrganization_Organization]
    FOREIGN KEY ([Organizations_Id])
    REFERENCES [dbo].[Organizations]
        ([Id])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_AccountOrganization_Organization'
CREATE INDEX [IX_FK_AccountOrganization_Organization]
ON [dbo].[AccountOrganization]
    ([Organizations_Id]);
GO

-- Creating foreign key on [Competitors_Id] in table 'CompetitorTournament'
ALTER TABLE [dbo].[CompetitorTournament]
ADD CONSTRAINT [FK_CompetitorTournament_Competitor]
    FOREIGN KEY ([Competitors_Id])
    REFERENCES [dbo].[Competitors]
        ([Id])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating foreign key on [Tournaments_Id] in table 'CompetitorTournament'
ALTER TABLE [dbo].[CompetitorTournament]
ADD CONSTRAINT [FK_CompetitorTournament_Tournament]
    FOREIGN KEY ([Tournaments_Id])
    REFERENCES [dbo].[Tournaments]
        ([Id])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_CompetitorTournament_Tournament'
CREATE INDEX [IX_FK_CompetitorTournament_Tournament]
ON [dbo].[CompetitorTournament]
    ([Tournaments_Id]);
GO

-- Creating foreign key on [TournamentId] in table 'Rounds'
ALTER TABLE [dbo].[Rounds]
ADD CONSTRAINT [FK_TournamentRound]
    FOREIGN KEY ([TournamentId])
    REFERENCES [dbo].[Tournaments]
        ([Id])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_TournamentRound'
CREATE INDEX [IX_FK_TournamentRound]
ON [dbo].[Rounds]
    ([TournamentId]);
GO

-- Creating foreign key on [RoundId] in table 'Pairings'
ALTER TABLE [dbo].[Pairings]
ADD CONSTRAINT [FK_RoundPairing]
    FOREIGN KEY ([RoundId])
    REFERENCES [dbo].[Rounds]
        ([Id])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_RoundPairing'
CREATE INDEX [IX_FK_RoundPairing]
ON [dbo].[Pairings]
    ([RoundId]);
GO

-- Creating foreign key on [OrganizationId] in table 'Tournaments'
ALTER TABLE [dbo].[Tournaments]
ADD CONSTRAINT [FK_OrganizationTournament]
    FOREIGN KEY ([OrganizationId])
    REFERENCES [dbo].[Organizations]
        ([Id])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_OrganizationTournament'
CREATE INDEX [IX_FK_OrganizationTournament]
ON [dbo].[Tournaments]
    ([OrganizationId]);
GO

-- Creating foreign key on [OrganizationId] in table 'Competitors'
ALTER TABLE [dbo].[Competitors]
ADD CONSTRAINT [FK_OrganizationCompetitor]
    FOREIGN KEY ([OrganizationId])
    REFERENCES [dbo].[Organizations]
        ([Id])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_OrganizationCompetitor'
CREATE INDEX [IX_FK_OrganizationCompetitor]
ON [dbo].[Competitors]
    ([OrganizationId]);
GO

-- Creating foreign key on [CompetitorId1] in table 'Pairings'
ALTER TABLE [dbo].[Pairings]
ADD CONSTRAINT [FK_PairingCompetitor]
    FOREIGN KEY ([CompetitorId1])
    REFERENCES [dbo].[Competitors]
        ([Id])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_PairingCompetitor'
CREATE INDEX [IX_FK_PairingCompetitor]
ON [dbo].[Pairings]
    ([CompetitorId1]);
GO

-- Creating foreign key on [CompetitorId2] in table 'Pairings'
ALTER TABLE [dbo].[Pairings]
ADD CONSTRAINT [FK_PairingCompetitor2]
    FOREIGN KEY ([CompetitorId2])
    REFERENCES [dbo].[Competitors]
        ([Id])
    ON DELETE NO ACTION ON UPDATE NO ACTION;
GO

-- Creating non-clustered index for FOREIGN KEY 'FK_PairingCompetitor2'
CREATE INDEX [IX_FK_PairingCompetitor2]
ON [dbo].[Pairings]
    ([CompetitorId2]);
GO

-- --------------------------------------------------
-- Script has ended
-- --------------------------------------------------