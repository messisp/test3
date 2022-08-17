
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
/**
 public static Long average(ParamBo param, List<Long> durationList) {
 return average(param.getAvgTimeCalSetting().getType(), param.getAvgTimeCalSetting().getType(), durationList);
 }
 */
    public Long getAverage() {
        return average;
    }

    public void setAverage(Long average) {
        this.average = average;
    }
    Long average;
    /**
         * @param type 0-全量平均; 1-去除离群平均; 2-截尾平均
         * @param ratio 截尾平均的比例(0-99)
         * */

        public static Long average(int type, int ratio, List<Long> durationList) {
            durationList.sort(Long::compare);
            int num=durationList.size();
            List<Long> durationAll;
            List<Long> durationOut;
            List<Long> durationSplit;
            Double avg=null;

            //全量list：durationAll
            durationAll = durationList;

            //去除离群值list：durationSplit
            int numQt=num-(int)(num * 0.75);
            int numTqt=num-(int)(num * 0.25);
            Long temp = durationList.get(numTqt-1)-durationList.get(numQt-1);
            double thresholdOut=temp.doubleValue()*1.5+durationList.get(numTqt-1).doubleValue();
            durationOut=durationList.stream().filter(x -> x <= thresholdOut).collect(Collectors.toList());

            //截尾list：durationSplit
            int numSplit=num-(int)(num*((double)ratio/100));
            Long thresholdSplit=durationList.get(numSplit-1);
            durationSplit=durationList.stream().filter(x -> x <= thresholdSplit).collect(Collectors.toList());

            //计算均值
            if(type==0) {
                //全量平均
                avg = durationAll.stream().collect(Collectors.averagingLong(x -> x));
            }else if(type==1){
                //离群平均
                avg=durationOut.stream().collect(Collectors.averagingLong(x->x));
            }else if(type==2){
                //截尾平均
                avg=durationSplit.stream().collect(Collectors.averagingLong(x->x));
            }
            if(avg!=null) {
                return avg.longValue();
            }else return null;
        }
        public static void main(String[] args) {
            Main a =new Main();
            List<Long> list =new ArrayList<Long>();
            list.add(123L);
            list.add(144L);
            list.add(165L);
            list.add(186L);
            list.add(207L);
            list.add(10000L);
            System.out.print(a.average(1,99,list));
        }
    }

