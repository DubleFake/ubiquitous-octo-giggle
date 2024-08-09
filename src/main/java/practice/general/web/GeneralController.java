package practice.general.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import practice.general.backend.Iphone;
import practice.general.backend.database.IphoneDatabase;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GeneralController {

    private IphoneDatabase idb;

    public GeneralController(){
        idb = new IphoneDatabase();
    }

    @GetMapping("/info")
    @ResponseBody
    public
        List<Iphone> getAllRecords() {
        return idb.getAllRecords();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/put")
    @ResponseBody
    public String putNewRecord(@RequestBody Iphone iphone) throws Exception {
        return idb.addNewRecord(iphone);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{ID}")
    @ResponseBody
    public String removeRecord(@PathVariable String ID) throws Exception {
        return idb.removeRecord(ID);
    }

    @GetMapping("/info/{ID}")
    @ResponseBody
    public List<Iphone> getOneRecord(@PathVariable String ID){
        return idb.getOneRecord(ID);
    }

}
