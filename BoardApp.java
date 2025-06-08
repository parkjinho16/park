BoardApp
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
