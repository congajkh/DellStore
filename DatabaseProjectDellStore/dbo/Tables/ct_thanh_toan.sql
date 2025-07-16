CREATE TABLE [dbo].[ct_thanh_toan] (
    [id]           INT             IDENTITY (1, 1) NOT NULL,
    [hoa_don_id]   INT             NULL,
    [hinh_thuc_id] INT             NULL,
    [so_tien]      DECIMAL (18, 2) NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

