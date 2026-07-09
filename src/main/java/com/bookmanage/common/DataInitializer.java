package com.bookmanage.common;

import com.bookmanage.entity.Book;
import com.bookmanage.entity.User;
import com.bookmanage.mapper.BookMapper;
import com.bookmanage.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 应用启动时初始化默认用户和图书种子数据
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserMapper userMapper;
    private final BookMapper bookMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initUsers();
        initBooks();
    }

    private void initUsers() {
        if (userMapper.selectByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("admin");
            admin.setCreateTime(LocalDateTime.now());
            userMapper.insert(admin);
        }
        if (userMapper.selectByUsername("user") == null) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole("user");
            user.setCreateTime(LocalDateTime.now());
            userMapper.insert(user);
        }
    }

    private void initBooks() {
        List<Book> existing = bookMapper.selectAll();
        if (!existing.isEmpty()) {
            return;
        }

        List<Book> books = Arrays.asList(
                createBook("活着", "余华", "作家出版社", "978-7-5063-0000-1", "文学", "讲述了农村人福贵悲惨的人生遭遇"),
                createBook("百年孤独", "加西亚·马尔克斯", "南海出版公司", "978-7-5442-0000-2", "文学", "魔幻现实主义经典代表作"),
                createBook("三体", "刘慈欣", "重庆出版社", "978-7-5366-0000-3", "文学", "地球文明与三体文明的首次交锋"),
                createBook("深入理解Java虚拟机", "周志明", "机械工业出版社", "978-7-111-0000-4", "计算机", "Java 虚拟机原理与实践"),
                createBook("算法导论", "Thomas H.Cormen", "机械工业出版社", "978-7-111-0000-5", "计算机", "算法设计与分析的经典教材"),
                createBook("数据库系统概论", "王珊", "高等教育出版社", "978-7-0400-0000-6", "计算机", "数据库系统原理入门教材"),
                createBook("时间简史", "史蒂芬·霍金", "湖南科学技术出版社", "978-7-5357-0000-7", "科学", "探索宇宙与时间的奥秘"),
                createBook("上帝掷骰子吗", "曹天元", "北京联合出版公司", "978-7-5502-0000-8", "科学", "量子力学的历史与哲学"),
                createBook("物种起源", "达尔文", "北京大学出版社", "978-7-3010-0000-9", "科学", "生物进化论的奠基之作"),
                createBook("万历十五年", "黄仁宇", "生活·读书·新知三联书店", "978-7-1080-0001-0", "历史", "以万历十五年为切入口剖析明代社会"),
                createBook("人类简史", "尤瓦尔·赫拉利", "中信出版社", "978-7-5086-0001-1", "历史", "人类从动物到上帝的历程"),
                createBook("明朝那些事儿", "当年明月", "中国海关出版社", "978-7-8016-0001-2", "历史", "以幽默笔触讲述明朝三百年历史"),
                createBook("苏菲的世界", "乔斯坦·贾德", "作家出版社", "978-7-5063-0001-3", "哲学", "以小说形式讲解西方哲学史"),
                createBook("沉思录", "马可·奥勒留", "中央编译出版社", "978-7-5117-0001-4", "哲学", "古罗马皇帝的心灵哲学笔记"),
                createBook("中国哲学简史", "冯友兰", "北京大学出版社", "978-7-3010-0001-5", "哲学", "中国哲学入门经典读物")
        );

        for (Book book : books) {
            bookMapper.insert(book);
        }
    }

    private Book createBook(String title, String author, String publisher, String isbn, String category, String description) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setIsbn(isbn);
        book.setCategory(category);
        book.setDescription(description);
        book.setStatus("available");
        book.setCreateTime(LocalDateTime.now());
        return book;
    }
}
