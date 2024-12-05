import { useEffect, useState } from "react"
import { Link } from "react-router-dom"


export default function HomePage() {
    const [contacts, setContacts] = useState([]);

    useEffect(() => {
        const fetchContacts = async () => {
            try {
                const response = await fetch("http://localhost:8080/contact");
                const data = await response.json();
                setContacts(data);
            } catch (error) {
                console.error("Error fetching contacts:", error);
            }
        };

        fetchContacts();
    }, []);

    return (
        <div className='w-full h-full flex flex-col'>
            <div className='w-full flex justify-around m-2'>
                <h1 className='text-xl font-bold'>ContactApp</h1>
                <Link to="/addContact" className='bg-sky-600 p-2 hover:bg-sky-900 text-white rounded-md'>
                    Add New Contact
                </Link>
            </div>
            <div className="w-full h-full grid grid-cols-4 gap-4">
                {contacts.map(contact => (
                    <div key={contact.id}>
                        <ContactCard contact={contact} />
                    </div>
                ))}
            </div>
        </div>
    );
}



function ContactCard({ contact: { id, name, email, phone, imageUrl } }) {
  return (
    <Link to={id}>
      <div className="w-full hover:scale-110 duration-100 h-fit p-4 flex flex-col items-center justify-center bg-white shadow-xl rounded-xl">
        <img src={imageUrl} className="w-[100px] h-[100px] rounded-full object-cover border border-gray-600" />
        <h1 className="text-xl">{name}</h1>
        <p>{email}</p>
        <p>{phone}</p>
      </div>
    </Link>
  );
}
