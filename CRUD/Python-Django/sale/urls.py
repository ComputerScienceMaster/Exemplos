from django.conf.urls import url
from sale import views
from sale.views import ViewSellProduct, ViewEditSale

urlpatterns = [
    url(r'^sellProduct/$', ViewSellProduct.as_view(), name="sell_product"),
    url(r'^sale/(?P<sale_id>\d+)$', views.show_sale, name='show_sale'),
    url(r'^removesale/(?P<sale_id>\d+)$', views.remove_sale, name='remove_sale'),
    url(r'^editsale/(?P<sale_id>\d+)$', ViewEditSale.as_view(), name='edit_sale'),
    url(r'^managesales/$', views.manage_sales, name='manage_sales'),
]
