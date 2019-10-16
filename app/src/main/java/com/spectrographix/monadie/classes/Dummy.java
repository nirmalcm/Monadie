package com.spectrographix.monadie.classes;

import com.spectrographix.monadie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user name on 2/28/2018.
 */

public class Dummy {

    public static final String PATH_MONGO_DUMMY_IMAGE ="http://139.59.3.107/mongo/projects/monadie/images/dummy/";

    public Dummy()
    {
    }

    public static List<Group> getDummyGroups()
    {
        List<Group> dummyGroups = new ArrayList<>();

        List<User> dummyUsers = getDummyUsers();

        for (int i=0;i<10;i++)
        {
            if (i==0)
            {
                Group group = new Group();
                group.setGroupId("group0");
                group.setGroupName("Friends");

                List<User> groupUsers= new ArrayList<>();
                for (int j=0;j<5;j++)
                {
                    if (j==0)
                        groupUsers.add(dummyUsers.get(4));
                    else if (j==1)
                        groupUsers.add(dummyUsers.get(3));
                    else if (j==2)
                        groupUsers.add(dummyUsers.get(2));
                    else if (j==3)
                        groupUsers.add(dummyUsers.get(1));
                }
                //group.setGroupMembers(groupUsers);
                dummyGroups.add(group);
            }
            if (i==1)
            {
                Group group = new Group();
                group.setGroupId("group1");
                group.setGroupName("Family");

                List<User> groupUsers= new ArrayList<>();
                for (int j=0;j<5;j++)
                {
                    if (j==0)
                        groupUsers.add(dummyUsers.get(5));
                    else if (j==1)
                        groupUsers.add(dummyUsers.get(2));
                    else if (j==2)
                        groupUsers.add(dummyUsers.get(3));
                }
                //group.setGroupMembers(groupUsers);
                dummyGroups.add(group);
            }
            if (i==0)
            {
                Group group = new Group();
                group.setGroupId("group3");
                group.setGroupName("Collegeus");

                List<User> groupUsers= new ArrayList<>();
                for (int j=0;j<5;j++)
                {
                    if (j==0)
                        groupUsers.add(dummyUsers.get(2));
                    else if (j==1)
                        groupUsers.add(dummyUsers.get(0));
                }
                //group.setGroupMembers(groupUsers);
                dummyGroups.add(group);
            }
        }

        return dummyGroups;
    }

    public static List<User> getDummyUsers()
    {
        List<User> dummyUsers = new ArrayList<>();

        for (int i=0;i<10;i++)
        {
            if(i==0)
            {
                User user = new User();
                user.setUserId("user0");
                user.setUserFirstName("Akhil");
                user.setUserLastName("Aki");
                user.setUserEmail("akhilaki@gmail.com");
                user.setUserPassword("spectrographix");
                user.setUserImage(PATH_MONGO_DUMMY_IMAGE+"akhil.png");
                dummyUsers.add(user);
            }
            else if(i==1)
            {
                User user = new User();
                user.setUserId("user1");
                user.setUserFirstName("Likhin");
                user.setUserLastName("Nelliyotan");
                user.setUserEmail("likhinnelliyotan@gmail.com");
                user.setUserPassword("spectrographix");
                user.setUserImage(PATH_MONGO_DUMMY_IMAGE+"likhin.png");
                dummyUsers.add(user);
            }
            else if(i==2)
            {
                User user = new User();
                user.setUserId("user2");
                user.setUserFirstName("Adarsh");
                user.setUserLastName("Kadayparth");
                user.setUserEmail("adarshkadayparth@gmail.com");
                user.setUserPassword("spectrographix");
                user.setUserImage(PATH_MONGO_DUMMY_IMAGE+"adarsh.png");
                dummyUsers.add(user);
            }
            else if(i==3)
            {
                User user = new User();
                user.setUserId("user3");
                user.setUserFirstName("Vivek");
                user.setUserLastName("Kumar");
                user.setUserEmail("vivekkumar@gmail.com");
                user.setUserPassword("spectrographix");
                user.setUserImage(PATH_MONGO_DUMMY_IMAGE+"vivekkumar.png");
                dummyUsers.add(user);
            }
            else if(i==4)
            {
                User user = new User();
                user.setUserId("user4");
                user.setUserFirstName("Athul");
                user.setUserLastName("Ashok");
                user.setUserEmail("athulashok@gmail.com");
                user.setUserPassword("spectrographix");
                user.setUserImage(PATH_MONGO_DUMMY_IMAGE+"athul.png");
                dummyUsers.add(user);
            }
            else if(i==5)
            {
                User user = new User();
                user.setUserId("user5");
                user.setUserFirstName("Vivek");
                user.setUserLastName("Venugopal");
                user.setUserEmail("vivekvenugopal@gmail.com");
                user.setUserPassword("spectrographix");
                user.setUserImage(PATH_MONGO_DUMMY_IMAGE+"vivekvenugopal.png");
                dummyUsers.add(user);
            }
        }

        return dummyUsers;
    }

    public static List<Product> getDummyProducts()
    {
        List<Product> dummyProducts = new ArrayList<>();

        for (int i=0;i<10;i++)
        {
            if(i==0)
            {
                Product product = new Product();
                product.setProductName("Audi a4");
                product.setProductDescription("Audi sedan, limited edition");
                product.setProductPrice("50000");
                product.setProductImage(PATH_MONGO_DUMMY_IMAGE+"audia4.png");
                product.setProductOwner("");
            }
            else if(i==1)
            {
                Product product = new Product();
                product.setProductName("Benz c Class");
                product.setProductDescription("All new c class, benz");
                product.setProductPrice("50000");
                product.setProductImage(PATH_MONGO_DUMMY_IMAGE+"benzcclass.png");
                product.setProductOwner("");
            }
            else if(i==2)
            {
                Product product = new Product();
                product.setProductName("Benz S Class");
                product.setProductDescription("The royal sedan, benz");
                product.setProductPrice("50000");
                product.setProductImage(PATH_MONGO_DUMMY_IMAGE+"benzsclass.png");
                product.setProductOwner("");
            }
            else if(i==3)
            {
                Product product = new Product();
                product.setProductName("Audi a6");
                product.setProductDescription("The classic sedan, audi");
                product.setProductPrice("50000");
                product.setProductImage(PATH_MONGO_DUMMY_IMAGE+"audia6.png");
                product.setProductOwner("");
            }
            else if(i==4)
            {
                Product product = new Product();
                product.setProductName("BMW X5");
                product.setProductDescription("The XUV mode, bmw");
                product.setProductPrice("50000");
                product.setProductImage(PATH_MONGO_DUMMY_IMAGE+"bmwx5.png");
                product.setProductOwner("");
            }
            else if(i==5)
            {
                Product product = new Product();
                product.setProductName("Audi r8");
                product.setProductDescription("Audi sport edition");
                product.setProductPrice("50000");
                product.setProductImage(PATH_MONGO_DUMMY_IMAGE+"audir8.png");
                product.setProductOwner("");
            }
            else if(i==7)
            {
                Product product = new Product();
                product.setProductName("FastTrack beta");
                product.setProductDescription("Fasttrack beta addition, red");
                product.setProductPrice("2800");
                product.setProductImage(PATH_MONGO_DUMMY_IMAGE+"fasttrack.png");
                product.setProductOwner("akhilaki@gmail.com");
                dummyProducts.add(product);
            }
            else if(i==8)
            {
                Product product = new Product();
                product.setProductName("Casio classic");
                product.setProductDescription("Analogue classico from casio !");
                product.setProductPrice("3200");
                product.setProductImage(PATH_MONGO_DUMMY_IMAGE+"casio.png");
                product.setProductOwner("akhilaki@gmail.com");
                dummyProducts.add(product);
            }
        }

        return dummyProducts;
    }

    public static List<Category> getDummyCategories()
    {
        List<Category> dummyCategories = new ArrayList<>();

        for (int i=0;i<10;i++)
        {
            if(i==0)
            {
            }
            else if(i==1)
            {
            }
            else if(i==2)
            {
            }
            else if(i==3)
            {
            }
            else if(i==4)
            {
            }
            else if(i==5)
            {
            }
        }

        return dummyCategories;
    }

    public static List<Category> getDummySubCategories()
    {
        List<Category> dummySubCategories = new ArrayList<>();
        for (int i=0;i<10;i++)
        {
            if(i==0)
            {
            }
            else if(i==1)
            {
            }
            else if(i==2)
            {
            }
            else if(i==3)
            {
            }
        }
        return dummySubCategories;
    }

    public static List<Auction> getDummyAuctions()
    {
        List<Auction> dummyAuctions = new ArrayList<>();
        for (int i=0;i<10;i++)
        {
            if(i==0)
            {
                Auction auction = new Auction();
                auction.setAuctionProductName("Audi a4");
                auction.setAuctionProductDescription("Audi sedan, limited edition");
                auction.setStartTime("1532088000");
                auction.setEndTime("1532599200");
                auction.setBasePrice("50000");
                auction.setAuctionProductImage(PATH_MONGO_DUMMY_IMAGE+"audia4.png");
                dummyAuctions.add(auction);
            }
            else if(i==1)
            {
                Auction auction = new Auction();
                auction.setAuctionProductName("Benz c Class");
                auction.setAuctionProductDescription("All new c class, Benz");
                auction.setStartTime("1532088000");
                auction.setEndTime("1532779200");
                auction.setBasePrice("55000");
                auction.setAuctionProductImage(PATH_MONGO_DUMMY_IMAGE+"benzcclass.png");
                dummyAuctions.add(auction);
            }
            else if(i==2)
            {
                Auction auction = new Auction();
                auction.setAuctionProductName("Benz S Class");
                auction.setAuctionProductDescription("The royal sedan , benz");
                auction.setStartTime("1532088000");
                auction.setEndTime("1532786400");
                auction.setBasePrice("60000");
                auction.setAuctionProductImage(PATH_MONGO_DUMMY_IMAGE+"benzsclass.png");
                dummyAuctions.add(auction);
            }
            else if(i==3)
            {
                Auction auction = new Auction();
                auction.setAuctionProductName("Audi a6");
                auction.setAuctionProductDescription("The classic sedan, audi");
                auction.setStartTime("1532088000");
                auction.setEndTime("1532793600");
                auction.setBasePrice("62000");
                auction.setAuctionProductImage(PATH_MONGO_DUMMY_IMAGE+"audia6.png");
                dummyAuctions.add(auction);
            }
            else if(i==4)
            {
                Auction auction = new Auction();
                auction.setAuctionProductName("Bmw X5");
                auction.setAuctionProductDescription("The XUV mode, bmw");
                auction.setStartTime("1532088000");
                auction.setEndTime("1532800800");
                auction.setBasePrice("68000");
                auction.setAuctionProductImage(PATH_MONGO_DUMMY_IMAGE+"bmwx5.png");
                dummyAuctions.add(auction);
            }
            else if(i==5)
            {
                Auction auction = new Auction();
                auction.setAuctionProductName("Audi r8");
                auction.setAuctionProductDescription("Audi sports edition");
                auction.setStartTime("1532088000");
                auction.setEndTime("1532808000");
                auction.setBasePrice("72000");
                auction.setAuctionProductImage(PATH_MONGO_DUMMY_IMAGE+"audir8.png");
                dummyAuctions.add(auction);
            }
        }
        return dummyAuctions;
    }
}