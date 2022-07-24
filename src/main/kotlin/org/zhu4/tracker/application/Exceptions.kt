package org.zhu4.tracker.application

import javax.ws.rs.BadRequestException

class PasswordNotMatch: BadRequestException("Password or username not match")
