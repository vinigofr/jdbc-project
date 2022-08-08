package app;

import java.util.Date;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("Prinf of seller by id 3 =====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
        System.out.println();

        System.out.println("Prinf of seller by departmentId 2 =====");
        List<Seller> sellers = sellerDao.findByDepartment(2);
        for (Seller s : sellers) {
            System.out.println("Vendedor " + s);
        }
        System.out.println();

        System.out.println("Prinf all seller=====");
        sellers = sellerDao.findAll();
        for (Seller s : sellers) {
            System.out.println("Vendedor " + s);
        }
        System.out.println();

        System.out.println("Prinf all seller=====");


        Department department = new Department(1, null);
        Seller seller1 = new Seller(null, "AAAAAAAAAAAA", "vinicius@vini.com", new Date(), 1.0, department);
        sellerDao.insert(seller1);

        System.out.println("Insertion seller =====");
        seller = sellerDao.findById(seller1.getId());
        System.out.println("Inserted! ID: " + seller.getId());
        System.out.println();

        System.out.println("Updating seller =====");
        seller = sellerDao.findById(1);
        seller.setName("Vinicius Novo");
        sellerDao.update(seller);
        System.out.println("Updated");

        System.out.println("Deleting seller =====");
        sellerDao.deleteById(30);
        System.out.println("Deleted");
    }
}
