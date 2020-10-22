package rentalService;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Return_table")
public class Return {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long rentalId;


    @PostPersist
    public void onPostPersist(){
        Returned returned = new Returned();
        BeanUtils.copyProperties(this, returned);
        returned.publishAfterCommit();

        try {
            Thread.currentThread().sleep((long) (800 + Math.random() * 220));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        rentalService.external.Collect collect = new rentalService.external.Collect();
        // mappings goes here

        collect.setRentalId(this.getRentalId());
        collect.setStatus("Collected");


        ReturnApplication.applicationContext.getBean(rentalService.external.CollectService.class)
            .collect(collect);

  //
    }

    @PreRemove
    public void onPreRemove(){
        ReturnCanceled returnCanceled = new ReturnCanceled();
        BeanUtils.copyProperties(this, returnCanceled);
        returnCanceled.publishAfterCommit();

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }




}
