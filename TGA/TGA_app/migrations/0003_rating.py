# -*- coding: utf-8 -*-
# Generated by Django 1.11.17 on 2022-12-29 03:40
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('TGA_app', '0002_place'),
    ]

    operations = [
        migrations.CreateModel(
            name='Rating',
            fields=[
                ('r_id', models.IntegerField(primary_key=True, serialize=False)),
                ('username', models.CharField(max_length=255)),
                ('rating_val', models.CharField(max_length=255)),
                ('pname', models.CharField(max_length=255)),
            ],
        ),
    ]
