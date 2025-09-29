package DellStore.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Thongketheongay {
    String maHD;
    String maNV;
    String tenNV;
    String hinhThuc;
    double tienMat;
    double tongTien;
}
