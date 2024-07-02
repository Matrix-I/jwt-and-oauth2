import jwt
from datetime import datetime, timedelta, timezone
from fastapi import FastAPI, Depends, HTTPException
from fastapi.security import OAuth2PasswordBearer
from pydantic import BaseModel

# Secret key to sign the JWT
SECRET_KEY = "f1e277ed620cf47cf21c22b5b96a7e44652d73963b0aae63b566d5901f8eb062bd57ff27f3f35b1bc91ce10d06e801e7e0ddbc0f7b32679aba41479e1baa27a0"
ALGORITHM = "HS512"
ACCESS_TOKEN_EXPIRE_MINUTES = 5

app = FastAPI()

# OAuth2 scheme for token
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")


class User(BaseModel):
    username: str


# Function to create a JWT token
def create_access_token(data: dict):
    to_encode = data.copy()
    expire = datetime.now(timezone.utc) + timedelta(minutes=ACCESS_TOKEN_EXPIRE_MINUTES)
    to_encode.update({"exp": expire})
    encoded_jwt = jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)
    return encoded_jwt


# Function to verify a JWT token
def verify_token(token: str = Depends(oauth2_scheme)):
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        username: str = payload.get("sub")
        if username is None:
            raise HTTPException(
                status_code=401, detail="Invalid authentication credentials"
            )
        return User(username=username)
    except jwt.PyJWTError:
        raise HTTPException(
            status_code=401, detail="Invalid authentication credentials"
        )


# Route to create a token
@app.post("/token")
async def login(username: str):
    access_token = create_access_token(data={"sub": username})
    return {"access_token": access_token, "token_type": "bearer"}


# Protected route that requires a valid token
@app.get("/protected")
async def protected_route(current_user: User = Depends(verify_token)):
    return {"message": f"Hello, {current_user.username}! This is a protected route."}


# Run with: uvicorn main:app --reload
