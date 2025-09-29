package DellStore.entity;

import java.math.BigDecimal;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SanPhamKhuyenMai {
    private int sanPhamId;
    private String maSanPham;
    private String tenSanPham;

    private int cpuId;
    private String cpu;

    private int gpuId;
    private String card;
    
    private String hang;

    private int ssdId;
    private String ocung;

    private int ramId;
    private String ram;

    private BigDecimal giaBan;
    
    private int soLuongBienThe;

}