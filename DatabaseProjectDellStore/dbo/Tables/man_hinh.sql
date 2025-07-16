CREATE TABLE [dbo].[man_hinh] (
    [id]           INT           IDENTITY (1, 1) NOT NULL,
    [kich_thuoc]   NVARCHAR (50) NULL,
    [do_phan_giai] NVARCHAR (50) NULL,
    [tan_so]       NVARCHAR (50) NULL,
    [loai_tam_nen] NVARCHAR (50) NULL,
    [trang_thai]   INT           NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

