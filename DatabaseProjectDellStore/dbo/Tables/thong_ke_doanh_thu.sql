CREATE TABLE [dbo].[thong_ke_doanh_thu] (
    [id]          INT             IDENTITY (1, 1) NOT NULL,
    [ngay]        DATE            NULL,
    [san_pham_id] INT             NULL,
    [so_luong]    INT             NULL,
    [doanh_thu]   DECIMAL (18, 2) NULL,
    [trang_thai]  INT             NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

