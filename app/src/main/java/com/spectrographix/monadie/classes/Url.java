package com.spectrographix.monadie.classes;

/**
 * Created by user name on 2/28/2018.
 */

public class Url {

    public static final String PATH_MONGO_PRODUCT_IMAGE ="http://139.59.3.107/mongo/projects/monadie/images/products/";
    public static final String PATH_MONGO_CATEGORY_IMAGE ="http://139.59.3.107/mongo/projects/monadie/images/categories/";

    public static final String MONGO_ACTIONS ="http://139.59.3.107/mongo/projects/monadie/actions/";

    public static final String MONGO_LOGIN_OR_REGISTER =MONGO_ACTIONS + "mongo-monadie-users-login_or_register.php";
    public static final String MONGO_CHANGE_PASSWORD =MONGO_ACTIONS + "change_password.php";
    public static final String MONGO_CHANGE_PHOTO =MONGO_ACTIONS + "user_edit_photo.php";
    public static final String MONGO_DETAILS_BY_EMAIL =MONGO_ACTIONS + "getusers_by_email.php";
    public static final String MONGO_UPDATE_DETAILS =MONGO_ACTIONS + "user_update_details.php";

    public static final String MONGO_PRODUCTS_GET_PRODUCTS = MONGO_ACTIONS + "mongo-monadie-products-get_products.php";
    public static final String MONGO_PRODUCTS_INSERT_PRODUCT = MONGO_ACTIONS + "mongo-monadie-products-insert_product.php";
    public static final String MONGO_PRODUCTS_REMOVE_PRODUCT_BY_PRODUCT_ID = MONGO_ACTIONS + "mongo-monadie-products-remove_product_by_product_id.php";
    public static final String MONGO_PRODUCTS_GET_PRODUCTS_BY_CATEGORY_ID = MONGO_ACTIONS + "mongo-monadie-products-get_products_by_category_id.php";

    public static final String MONGO_CATEGORIES_GET_PARENT_CATEGORIES = MONGO_ACTIONS + "mongo-monadie-categories-get_parent_categories.php";
    public static final String MONGO_CATEGORIES_GET_CATEGORIES_EXCEPT_PARENT = MONGO_ACTIONS + "mongo-monadie-categories-get_categories_except_parent.php";
    public static final String MONGO_CATEGORIES_GET_CHILD_CATEGORIES_BY_PARENT_ID = MONGO_ACTIONS + "mongo-monadie-categories-get_child_categories_by_parent_id.php";
    public static final String MONGO_CATEGORIES_INSERT_CATEGORY = MONGO_ACTIONS + "mongo-monadie-categories-insert_category.php";

    public static final String MONGO_AUCTIONS_GET_AUCTION_BY_PRODUCT_ID = MONGO_ACTIONS + "mongo-monadie-auctions";
    public static final String MONGO_AUCTIONS_GET_AUCTION_BY_AUCTION_ID = MONGO_ACTIONS + "mongo-monadie-auctions";
    public static final String MONGO_AUCTIONS_REMOVE_AUCTION_BY_AUCTION_ID_AND_PRODUCT_ID = MONGO_ACTIONS + "mongo-monadie-auctions";
    public static final String MONGO_AUCTIONS_MAKE_A_BID = MONGO_ACTIONS + "mongo-monadie-auctions";
    public static final String MONGO_AUCTIONS_INSERT_AUCTION = MONGO_ACTIONS + "mongo-monadie-auctions";

    public static final String MONGO_AUCTIONS_CREATE_AUCTION = MONGO_ACTIONS + "mongo-monadie-auctions-create_auction.php";
    public static final String MONGO_AUCTIONS_GET_PUBLIC_AUCTIONS = MONGO_ACTIONS + "mongo-monadie-auctions-get_public_auctions.php";
    public static final String MONGO_AUCTIONS_GET_PRIVATE_AUCTIONS = MONGO_ACTIONS + "mongo-monadie-auctions-get_private_auctions.php";
    public static final String MONGO_AUCTIONS_GET_CREATED_AUCTIONS = MONGO_ACTIONS + "mongo-monadie-auctions-get_created_auctions.php";

    public static final String MONGO_USERS_GET_ALL_USERS = MONGO_ACTIONS + "mongo-monadie-users-get_all_users.php";

    public static final String MONGO_GROUPS_CREATE_GROUP = MONGO_ACTIONS + "mongo-monadie-groups-create_group.php";
    public static final String MONGO_GROUPS_GET_ALL_GROUPS = MONGO_ACTIONS + "mongo-monadie-groups-get_all_groups.php";
    public static final String MONGO_GROUPS_GET_GROUP_MEMBERS = MONGO_ACTIONS + "mongo-monadie-groups-get_group_members.php";
    public static final String MONGO_GROUPS_GET_GROUP_MEMBERS_WITH_STATUS = MONGO_ACTIONS + "mongo-monadie-groups-get_group_members_with_status.php";
    public static final String MONGO_GROUPS_DELETE_GROUP = MONGO_ACTIONS + "mongo-monadie-groups-delete_group.php";

    public static final String MONGO_BIDDING_DETAILS_GET_BIDDING_DETAILS_BY_AUCTION_ID = MONGO_ACTIONS + "mongo-monadie-bidding-details";

    ////////////////////////////////////////////////////////////////////////////////////////


    public static final String USERS = "http://139.59.3.107/monadie/actions/users.php";
    public static final String PRODUCTS = "http://139.59.3.107/monadie/actions/products.php";
    public static final String CATEGORIES = "http://139.59.3.107/monadie/actions/categories.php";
    public static final String AUCTIONS = "http://139.59.3.107/monadie/actions/auctions.php";
    public static final String BIDDING_DETAILS = "http://139.59.3.107/monadie/actions/bidding_details.php";

    ////////////////////////////////////////////////////////////////////////////////////////

    /*public static final String COMMON_PATH_ACTIONS ="http://139.59.3.107/monadie/actions/";
    public static final String COMMON_PATH_IMAGES ="http://139.59.3.107/monadie/img/";

    public static final String PRODUCT_IMAGE_PATH ="http://139.59.3.107/monadie/img/product/";
    public static final String CATEGORY_IMAGE_PATH ="http://139.59.3.107/monadie/img/category/";

    //public static final String URL_REGISTER = "http://139.59.3.107/monadie/actions/monadie-login.php";
    //public static final String URL_LOGIN = "http://139.59.3.107/monadie/actions/monadie-login.php";

    public static final String URL_PRODUCTS = "http://139.59.3.107/monadie/actions/monadie-products_display.php";
    public static final String URL_PRODUCTS_INSERT = "http://139.59.3.107/monadie/actions/monadie-product_insert.php";
    public static final String URL_PRODUCT_REMOVE_PRODUCT = "http://139.59.3.107/monadie/actions/monadie-product-remove_product.php";
    public static final String URL_CHILD_PRODUCTS = "http://139.59.3.107/monadie/actions/monadie-categories-get_products_sub_category.php";

    public static final String URL_CATEGORIES = "http://139.59.3.107/monadie/actions/monadie-categories_display.php";
    public static final String URL_SUB_CATEGORIES = "http://139.59.3.107/monadie/actions/monadie-categories-get_sub_categories.php";
    public static final String URL_CHILD_CATEGORIES = "http://139.59.3.107/monadie/actions/monadie-categories-get-child-categories.php";
    public static final String URL_CHILD_CATEGORIES_BY_PARENT = "http://139.59.3.107/monadie/actions/monadie-categories-get_sub_categories_by_parent.php";
    public static final String URL_CATEGORY_INSERT_CATEGORY = COMMON_PATH_ACTIONS + "monadie-categories_insert.php";

    public static final String URL_AUCTION_GET_AUCTION_DETAILS_FOR_PRODUCT = "http://139.59.3.107/monadie/actions/monadie-auction-get_auction_details_for_product.php";
    public static final String URL_AUCTION_REMOVE_PRODUCT_FROM_AUCTION = "http://139.59.3.107/monadie/actions/monadie-auction-remove_from_auction.php";
    public static final String URL_AUCTION_MAKE_A_BID = "http://139.59.3.107/monadie/actions/monadie-auction-make_a_bid.php";
    public static final String URL_AUCTION_GET_BIDDING_DETAILS = COMMON_PATH_ACTIONS + "monadie-auction-get_bidding_details.php";
    public static final String URL_AUCTION_GET_CURRENT_AUCTION_DETAILS = "http://139.59.3.107/monadie/actions/monadie-auction-get_current_auction_details.php";
    public static final String URL_AUCTION_INSERT_INTO_AUCTION = "http://139.59.3.107/monadie/actions/monadie-auction-insert_into_auction.php";*/
}
