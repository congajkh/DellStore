CREATE TABLE [dbo].[chi_tiet_san_pham] (
    [id]          INT             IDENTITY (1, 1) NOT NULL,
    [san_pham_id] INT             NULL,
    [ram_id]      INT             NULL,
    [cpu_id]      INT             NULL,
    [ocung_id]    INT             NULL,
    [card_id]     INT             NULL,
    [hang_id]     INT             NULL,
    [serial]      NVARCHAR (50)   NULL,
    [gia_ban]     DECIMAL (18, 2) NULL,
    [trang_thai]  INT             NULL,
    PRIMARY KEY CLUSTERED ([id] ASC),
    CONSTRAINT [FK_ctsp_card] FOREIGN KEY ([card_id]) REFERENCES [dbo].[card] ([id]),
    CONSTRAINT [FK_ctsp_cpu] FOREIGN KEY ([cpu_id]) REFERENCES [dbo].[cpu] ([id])
);


GO

