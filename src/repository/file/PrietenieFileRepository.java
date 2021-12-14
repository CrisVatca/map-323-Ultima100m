package repository.file;

import domain.Prietenie;
import domain.Utilizator;
import domain.validators.Validator;

import java.util.List;

public class PrietenieFileRepository extends AbstractFileRepository<Long, Prietenie>{

    private static Long nextId = 0L;

    public PrietenieFileRepository(String fileName, Validator<Prietenie> validator) {
        super(fileName, validator);
        nextId++;
    }

    @Override
    public Prietenie extractEntity(List<String> attributes) {
        long currentId = Long.parseLong(attributes.get(0));

        if (currentId > nextId) {
            nextId = currentId;
        }
        long id1 = Long.parseLong(attributes.get(1));
        long id2 = Long.parseLong(attributes.get(2));
        Prietenie p = new Prietenie(id1, id2);
        p.setId(Long.parseLong(attributes.get(0)));
        return p;
    }


    @Override
    public Prietenie save(Prietenie entity) {
        entity.setId(nextId);
        nextId++;
        return super.save(entity);
    }

    @Override
    public String createEntityAsString(Prietenie entity) {
        return entity.getId().toString() + ";" + entity.getIdU().toString() + ";" + entity.getIdP().toString() + '\n';
    }

}
