package com.scp.hello.process;

import com.scp.hello.entity.Person;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(final Person person) throws Exception {
        System.out.println("--处理中--FirstName:-"+person.getFirstName()+" > "+person.getFirstName().toUpperCase()+
                "--LastName--"+person.getLastName()+" > "+person.getLastName().toUpperCase());
        return new Person(person.getFirstName().toUpperCase(), person.getLastName().toUpperCase());
    }
}
