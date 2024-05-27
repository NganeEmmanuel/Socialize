import axios from "axios"

export const api = axios.create({
    baseURL : "http://localhost:8080"
})


/* test function to get request from backend rest api */
export async  function getHome(){
    try {
        const response = await api.get("/home")
        return response.data
    }catch (error){       

        throw new Error("Error getting the home page")
    }
}