package com.example.univallealtoque.model

data class LoginResponseExpress(
    val userData: UserDataResponseExpress? = null,
    val token: String? = null,
    val message: String? = null
)

data class UserDataResponseExpress(
    val user_id: Int?,
    val name: String?,
    val last_name: String?,
    var profile_photo: String?,
    val email: String?,
    var program: String?,
    var phone: String?,
    val password: String?,
)

data class RecoverPasswordResponse(
    val message: String? = null,
    val randomCode: Int,
    val expirationDateString: String? = null
)

data class SendCodeDeleteAccountResponse(
    val message: String? = null
)

data class DeleteAccountResponse(
    val message: String? = null
)

data class ChangePasswordResponse(
    val message: String? = null,
)

data class NewPasswordResponse(
    val message: String? = null,
)

data class EnrolledActivitiesResponse(
    val message: String? = null,
    val activities: List<ActivitiesList>
)

data class ActivitiesList(
    val group_id: Int? = null,
    val group_name: String? = null,
    val group_description: String? = null,
    val group_photo: String? = null,
    val event_id: Int? = null,
    val event_name: String? = null,
    val event_description: String? = null,
    val event_photo: String? = null
)


data class SemilleroResponse(
    val message: String? = null,
    val semilleroInfoArray: List<semillerosList>,
    val isUserEnrolled: Boolean = false,
)

data class semillerosList(
    val group_name: String? = null,
    val group_description: String? = null,
    val slots: String? = null,
    val available_slots: String? = null,
    val monday_start: String? = null,
    val monday_end: String? = null,
    val tuesday_start: String? = null,
    val tuesday_end: String? = null,
    val wednesday_start: String? = null,
    val wednesday_end: String? = null,
    val thursday_start: String? = null,
    val thursday_end: String? = null,
    val friday_start: String? = null,
    val friday_end: String? = null,
    val saturday_start: String? = null,
    val saturday_end: String? = null,
    val photo: String? = null,
    val place: String? = null,
    val slots_taken: String? = null
)

data class EventResponse(
    val message: String? = null,
    val eventInfoArray: List<eventList>,
    val isUserEnrolled: Boolean = false,
)

data class eventList(
    val event_name: String? = null,
    val event_description: String? = null,
    val slots: String? = null,
    val available_slots: String? = null,
    val date: String? = null,
    val hour_start: String? = null,
    val hour_end: String? = null,
    val photo: String? = null,
    val place: String? = null,
    val slots_taken: String? = null
)

data class EventsListResponse(
    val message: String? = null,
    val events: List<EventsList>
)

data class EventsList(
    val event_id: Int? = null,
    val event_name: String? = null,
    val photo: String? = null

)

data class ErrollmentResponse(
    val message: String? = null,
)

data class CancelErrollmentResponse(
    val message: String? = null,
)

data class SemillerosListResponse(
    val message: String? = null,
    val activities: List<SemillerosHomeList>
)

data class SemillerosHomeList(
    val group_id: Int? = null,
    val group_name: String? = null,
    val group_description: String? = null,
    val photo: String? = null
)