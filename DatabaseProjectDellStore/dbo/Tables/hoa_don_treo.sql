CREATE TABLE [dbo].[hoa_don_treo] (
    [id]         INT            IDENTITY (1, 1) NOT NULL,
    [hoa_don_id] INT            NULL,
    [ghi_chu]    NVARCHAR (255) NULL,
    [trang_thai] INT            NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

