CREATE TABLE [dbo].[ct_hoa_don] (
    [id]         INT             IDENTITY (1, 1) NOT NULL,
    [hoa_don_id] INT             NULL,
    [serial_id]  INT             NULL,
    [don_gia]    DECIMAL (18, 2) NULL,
    [trang_thai] INT             NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

