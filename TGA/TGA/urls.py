"""TGA URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url
from django.contrib import admin
from TGA_app.views import *

urlpatterns = [
    url(r'^admin/', admin.site.urls),

    url(r'^register',register,name="register"),
    url(r'^find_login',find_login,name="find_login"),
    url(r'^get_registered_users',get_registered_users,name="get_registered_users"),
    url(r'^Add_place',Add_place,name="Add_place"),
    url(r'^view_places_admin',view_places_admin,name="view_places_admin"),
    url(r'^delete',delete,name="delete"),
    url(r'^Search_place',Search_place,name="Search_place"),
    url(r'^Upload_Excel',Upload_Excel,name="Upload_Excel"),
    url(r'^Give_rating_one',Give_rating_one,name="Give_rating_one"),
    url(r'^Give_rating_two',Give_rating_two,name="Give_rating_two"),
    url(r'^users_list',users_list,name="users_list"),
    url(r'^filter_',filter_,name="filter_"),
    url(r'^send_message',send_message,name="send_message"),
    url(r'^check_requests',check_requests,name="check_requests"),
    url(r'^sent_req',sent_req,name="sent_req"),
    url(r'^get_my_requests',get_my_requests,name="get_my_requests"),
    url(r'^accept_request',accept_request,name="accept_request"),
    url(r'^reject_request',reject_request,name="reject_request"),


]
