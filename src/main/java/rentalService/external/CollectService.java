
package rentalService.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@FeignClient(name="Collect", url="${api.collect.url}")
public interface CollectService {

    @RequestMapping(method= RequestMethod.POST, path="/collects")
    public void collect(@RequestBody Collect collect);

}