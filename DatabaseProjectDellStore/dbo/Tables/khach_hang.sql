CREATE TABLE [dbo].[khach_hang] (
    [id]          INT            IDENTITY (1, 1) NOT NULL,
    [ten]         NVARCHAR (100) NULL,
    [ngay_sinh]   DATE           NULL,
    [gioi_tinh]   NVARCHAR (10)  NULL,
    [sdt]         NVARCHAR (20)  NULL,
    [email]       NVARCHAR (100) NULL,
    [diem_thuong] INT            NULL,
    [hang]        NVARCHAR (50)  NULL,
    [trang_thai]  INT            NULL,
    PRIMARY KEY CLUSTERED ([id] ASC)
);


GO

