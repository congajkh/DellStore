CREATE VIEW [dbo].[vw_chi_tiet_san_pham_full] AS
SELECT ct.id, sp.ten AS ten_san_pham, cpu.ten AS ten_cpu, ram.ten AS ten_ram,
       oc.ten AS ten_ocung, card.ten AS ten_card, h.ten AS ten_hang,
       ct.serial, ct.gia_ban, ct.trang_thai
FROM chi_tiet_san_pham ct
JOIN san_pham sp ON ct.san_pham_id = sp.id
JOIN cpu ON ct.cpu_id = cpu.id
JOIN ram ON ct.ram_id = ram.id
JOIN ocung oc ON ct.ocung_id = oc.id
JOIN card ON ct.card_id = card.id
JOIN hang h ON ct.hang_id = h.id;

GO

