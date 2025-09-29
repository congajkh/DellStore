package DellStore.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongketheosanphamDTO {
    String MaSP;
    String TenSP;
    int SLBan;
    double DoanhThu;
    double Gia;
}
