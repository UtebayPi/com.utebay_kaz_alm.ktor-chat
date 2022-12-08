package com.utebay_kaz_alm.room

class MemberAlreadyExistsException : Exception(
    "There is already a member with that username in that room"
)