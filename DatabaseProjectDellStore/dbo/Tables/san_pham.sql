CREATE TABLE [dbo].[san_pham] (
    [id]               INT            IDENTITY (1, 1) NOT NULL,
    [ten]              NVARCHAR (255) NULL,
    [mo_ta]            NVARCHAR (255) NULL,
    [loai_san_pham_id] INT            NULL,
    [hang_id]          INT            NULL,
    [trang_thai]       INT            NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

