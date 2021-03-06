package edu.miu.cs545.group5.onlinemarket.domain;

import com.google.common.io.ByteStreams;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private Integer stock;

    @NotBlank
    @Lob
    private String description;


    private String imageName;

    private String fileType;

    @Lob
    private byte[] data;

    @Transient
    private MultipartFile multipartFile;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn
    //@NotNull
    private Seller seller;

    @OneToMany(mappedBy = "product")
    private List<ProductReview> reviews;

    public Product() {
    }

    public Product(@NotBlank String name,
                   @NotNull Double price,
                   @NotNull Integer stock,
                   @NotBlank String description,
                   String imageName,
                   @NotNull Category category,
                   Seller seller) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.imageName = imageName;
        this.category = category;
        this.seller = seller;
        this.reviews = new ArrayList<>();

        if(imageName != null){
            try {
                ClassLoader classLoader = getClass().getClassLoader();
                String rootDirectory = URLDecoder.decode(classLoader.getResource(".").getFile(), "UTF-8");
                File newFile = new File(rootDirectory + "static/images/" + imageName);
                FileInputStream stream = new FileInputStream(newFile);
                this.data = Files.readAllBytes(newFile.toPath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<ProductReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<ProductReview> reviews) {
        this.reviews = reviews;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Transient
    private ProductReview currentUserReview;

    public ProductReview getCurrentUserReview() {
        return currentUserReview;
    }

    public void setCurrentUserReview(ProductReview currentUserReview) {
        this.currentUserReview = currentUserReview;
    }

    public void updateCurrentUserReviewIfNeeded(Long id) {
        for (ProductReview review : reviews) {
            if (review.getBuyer().getId() == id) {
                this.setCurrentUserReview(review);
                return;
            }
        }
    }
}
