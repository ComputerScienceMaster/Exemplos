from django.conf.urls import url
from user_account.views import register_user_view
from user_profile import views

urlpatterns = [
	url(r'^$', views.index, name='index'),
	url(r'^profiles/(?P<profile_id>\d+)$', views.show_profile, name='show_profile'),
]