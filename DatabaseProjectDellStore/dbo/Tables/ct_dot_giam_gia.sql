CREATE TABLE [dbo].[ct_dot_giam_gia] (
    [id]              INT             IDENTITY (1, 1) NOT NULL,
    [dot_giam_gia_id] INT             NULL,
    [chi_tiet_sp_id]  INT             NULL,
    [muc_giam]        DECIMAL (18, 2) NULL,
    [loai_giam]       NVARCHAR (20)   NULL,
    [trang_thai]      INT             NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

