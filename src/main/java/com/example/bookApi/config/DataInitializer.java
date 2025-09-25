package com.example.bookApi.config;
import com.example.bookApi.model.entity.Book;
import com.example.bookApi.model.entity.User;
import com.example.bookApi.model.enums.BookStatus;
import com.example.bookApi.model.enums.UserRole;
import com.example.bookApi.model.enums.UserStatus;
import com.example.bookApi.repository.BookRepository;
import com.example.bookApi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository, UserRepository userRepository) {
        return args -> {
            if(bookRepository.count() == 0 ){
                bookRepository.save(new Book("Book","author", 15, "fiction", BookStatus.AVAILABLE));
                bookRepository.save(new Book("Book2","author2", 20, "fantasy", BookStatus.DELETED));
                bookRepository.save(new Book("Book3","author3", 25, "romance"));
            }

            if(userRepository.count() ==  0) {
                userRepository.save(new User("Doe", "john","password","user@gmail.com",  UserStatus.ACTIVE, UserRole.CUSTOMER));
                userRepository.save(new User("root", "root","root","root@gmail.com", UserStatus.ACTIVE, UserRole.ADMIN));
            }
        };
    }
}