package domain.validators;

import domain.Message;

public class MessageValidator implements Validator<Message> {
    @Override
    public void validate(Message entity) throws ValidationException{
        if(entity.getId() == null){
            throw new ValidationException("Id-ul nu poate fi null.");
        }
        if(entity.getTo() == null){
            throw new ValidationException("Persoana careia se trimite mesajul trebuie sa existe.");
        }
        if(entity.getFrom() == null) {
            throw new ValidationException("Persoana de la care se primeste mesajul trebuie sa existe.");
        }
    }
}
