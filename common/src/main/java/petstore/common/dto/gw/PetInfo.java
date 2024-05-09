package petstore.common.dto.gw;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetInfo {
  private String id;
  private String store;
  private String name;
  private String category;
  private String status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate birthday;

  public static void main(String[] args){
    System.out.println(romanToInt("MCMXCIV"));
  }

  public static int romanToInt(String s) {
    var menu = new int[2000];
    menu['I'] = 1;
    menu['V'] = 5;
    menu['X'] = 10;
    menu['L'] = 50;
    menu['C'] = 100;
    menu['D'] = 500;
    menu['M'] = 1000;

    var cA = s.toCharArray();
    int result = 0;

    for (int i = cA.length - 1; i >=0 ; i--) {
      int curr = menu[cA[i]];
      result += curr;

      if (i-1 >= 0) {
        int next = menu[cA[i-1]];
        if (curr > next) {
          result -= next;
          i--;
        }

      }
    }

    return result;
  }
}
