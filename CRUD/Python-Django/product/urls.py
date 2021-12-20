from django.conf.urls import url
from product import views
from product.views import ViewAddProduct, ViewEditProduct

urlpatterns = [
    url(r'^addproduct/$', ViewAddProduct.as_view(), name="add_product"),
    url(r'^product/(?P<product_id>\d+)$', views.show_product, name='show_product'),
    url(r'^removeProduct/(?P<product_id>\d+)$', views.remove_product, name='remove_product'),
    url(r'^editproduct/(?P<product_id>\d+)$', ViewEditProduct.as_view(), name='edit_product'),
    url(r'^manageproduct/$', views.manage_products, name='manageproduct'),
]
