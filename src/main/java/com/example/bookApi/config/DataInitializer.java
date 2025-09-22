package com.example.bookApi.config;
import com.example.bookApi.model.Book;
import com.example.bookApi.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository) {
        return args -> {
            if(bookRepository.count() == 0 ){
                bookRepository.save(new Book("Book","author", 15, "fiction"));
                bookRepository.save(new Book("Book2","author2", 20, "fantasy"));
                bookRepository.save(new Book("Book3","author3", 25, "romance"));
            }
        };
    }
}