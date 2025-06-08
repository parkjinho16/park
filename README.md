게시판 만들기!
---------------------------------------
회원가입 / 로그인	users 테이블, 로그인 성공 여부 처리
글쓰기	게시글 저장 (제목, 내용, 작성자, 작성일)
글 목록 보기	글 번호, 제목, 작성자 출력
글 상세 보기	글 번호 선택 → 내용 전체 보기
글 삭제	본인이 쓴 글만 삭제 가능 (간단한 인증 처리)

---------------------------------------
데이터 베이스 구성
user(회원정보)
CREATE TABLE users (
    user_id     INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(30) NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    email       VARCHAR(100),
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP
);

posts (게시글)
CREATE TABLE posts (
    post_id     INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT NOT NULL,
    title       VARCHAR(100),
    content     TEXT,
    views       INT DEFAULT 0,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

comments (댓글)
CREATE TABLE comments (
    comment_id  INT AUTO_INCREMENT PRIMARY KEY,
    post_id     INT NOT NULL,
    user_id     INT NOT NULL,
    content     TEXT,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts(post_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

categories (게시판 분류)
CREATE TABLE categories (
    category_id   INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(50) NOT NULL
);

post_likes (게시글 좋아요)
CREATE TABLE post_likes (
    user_id     INT,
    post_id     INT,
    liked_at    DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (post_id) REFERENCES posts(post_id)
);

comment_likes (댓글 좋아요)
CREATE TABLE comment_likes (
    user_id      INT,
    comment_id   INT,
    liked_at     DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, comment_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (comment_id) REFERENCES comments(comment_id)
);

files (게시글 첨부파일)
CREATE TABLE files (
    file_id     INT AUTO_INCREMENT PRIMARY KEY,
    post_id     INT,
    file_name   VARCHAR(255),
    file_path   VARCHAR(255),
    uploaded_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts(post_id)
);

login_history (접속 로그)
CREATE TABLE login_history (
    log_id     INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT,
    ip_address VARCHAR(45),
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-----------------------------------------------------------------------------------------------
java 코드


import java.util.ArrayList;
import java.util.Scanner;

class Post {
    private int id;
    private String title;
    private String content;

    public Post(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void displaySummary() {
        System.out.println(id + ". " + title);
    }

    public void displayDetail() {
        System.out.println("글 번호: " + id);
        System.out.println("제목: " + title);
        System.out.println("내용: " + content);
    }
}

public class BoardApp {
    static ArrayList<Post> posts = new ArrayList<>();
    static int postIdCounter = 1;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n📋 게시판 메뉴");
            System.out.println("1. 글 작성");
            System.out.println("2. 글 목록 보기");
            System.out.println("3. 글 상세 보기");
            System.out.println("4. 글 삭제");
            System.out.println("5. 종료");
            System.out.print("번호를 선택하세요: ");
            int choice = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기

            switch (choice) {
                case 1:
                    writePost(sc);
                    break;
                case 2:
                    listPosts();
                    break;
                case 3:
                    viewPostDetail(sc);
                    break;
                case 4:
                    deletePost(sc);
                    break;
                case 5:
                    System.out.println("게시판을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }

    public static void writePost(Scanner sc) {
        System.out.print("제목을 입력하세요: ");
        String title = sc.nextLine();
        System.out.print("내용을 입력하세요: ");
        String content = sc.nextLine();

        Post newPost = new Post(postIdCounter++, title, content);
        posts.add(newPost);
        System.out.println("✅ 글이 등록되었습니다.");
    }

    public static void listPosts() {
        System.out.println("\n📄 글 목록");
        if (posts.isEmpty()) {
            System.out.println("등록된 글이 없습니다.");
        } else {
            for (Post p : posts) {
                p.displaySummary();
            }
        }
    }

    public static void viewPostDetail(Scanner sc) {
        System.out.print("확인할 글 번호를 입력하세요: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Post p : posts) {
            if (p.getId() == id) {
                System.out.println("\n🔎 글 상세 내용");
                p.displayDetail();
                return;
            }
        }
        System.out.println("⚠️ 해당 번호의 글이 없습니다.");
    }

    public static void deletePost(Scanner sc) {
        System.out.print("삭제할 글 번호를 입력하세요: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Post p : posts) {
            if (p.getId() == id) {
                posts.remove(p);
                System.out.println("🗑️ 글이 삭제되었습니다.");
                return;
            }
        }
        System.out.println("⚠️ 해당 번호의 글이 없습니다.");
    }
}

