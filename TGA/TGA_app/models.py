from django.db import models

# Create your models here.
class Users(models.Model):
    u_id = models.IntegerField(primary_key=True)
    name=models.CharField(max_length=255)
    address = models.CharField(max_length=255)
    mobile = models.CharField(max_length=255)
    email=models.CharField(max_length=255)
    username=models.CharField(max_length=255)
    password = models.CharField(max_length=255)

class Friends(models.Model):
    f_id = models.IntegerField(primary_key=True)
    username=models.CharField(max_length=255)
    friends_name = models.TextField()


class FR_Table(models.Model):
    r_id = models.IntegerField(primary_key=True)
    sender_username=models.CharField(max_length=255)
    receiver_username = models.CharField(max_length=255)




class Place(models.Model):
    p_id = models.IntegerField(primary_key=True)
    pname=models.CharField(max_length=255)
    ptype = models.CharField(max_length=255)
    rating = models.CharField(max_length=255)
    o_time=models.CharField(max_length=255)
    c_time=models.CharField(max_length=255)
    amount = models.CharField(max_length=255)
    latitude=models.CharField(max_length=255)
    longitude=models.CharField(max_length=255)
    link = models.CharField(max_length=255)

class Rating(models.Model):
    r_id = models.IntegerField(primary_key=True)
    username=models.CharField(max_length=255)
    rating_val = models.CharField(max_length=255)
    pname=models.CharField(max_length=255)

class Chat(models.Model):
    m_id = models.IntegerField(primary_key=True)
    sender=models.CharField(max_length=255)
    receiver = models.CharField(max_length=255)
    datetime=models.CharField(max_length=255)
    message=models.TextField()
