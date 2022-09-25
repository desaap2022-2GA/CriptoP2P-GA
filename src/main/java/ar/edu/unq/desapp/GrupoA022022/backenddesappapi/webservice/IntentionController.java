package ar.edu.unq.desapp.GrupoA022022.backenddesappapi.webservice;

import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Cryptocurrency;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.ICryptocurrencyRepo;
import ar.edu.unq.desapp.GrupoA022022.backenddesappapi.persistence.IIntentionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/intentions")
public class IntentionController {
/*
    @Autowired
    IIntentionRepo intentionRepo;

    @Autowired
    ICryptocurrencyRepo cryptocurrencyRepo;

    @PutMapping("/{intentionId}/cryptocurrency/{cryptocurrencyId}")
    Intention assignCryptocurrencyToIntention(
            @PathVariable Long intentionId,
            @PathVariable Long cryptocurrencyId
    ) {
        Intention intention = intentionRepo.findById(Math.toIntExact(intentionId)).get();
        Cryptocurrency cryptocurrency = cryptocurrencyRepo.findById(Math.toIntExact(cryptocurrencyId)).get();
        intention.setCryptocurrency(cryptocurrency);
        return intentionRepo.save(intention);
    }

*/
}
