from django.conf.urls import url
from django.contrib.auth.views import login, logout_then_login
from user_account.views import register_user_view

urlpatterns = [
    url(r'^register/$', register_user_view.as_view(), name="register"),
    url(r'^login/$', login ,{'template_name': 'login.html'}, name='login'),
    url(r'^logout/$', logout_then_login, {'login_url':'/login/'}, name='logout')
]
