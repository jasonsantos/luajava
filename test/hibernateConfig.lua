return 
    {
        name="class",
        attributes={{name="name", value="Recipient"}, {name="table", value="RECIPIENTS"}},
        body=
        {
            {
                name="cache",
                attributes={{name="usage", value="read-write"}},
                body={}
            },
            {
                name="id",
                attributes={{name="name", value="id"}, {name="type", value="long"}},
                body=
                {
                    {
                        name="generator",
                        attributes={{name="class", value="native"}},
                        body={}
                    }
                }
            },
            {
                name="property",
                attributes={{name="name", value="name"}, {name="type", value="string"}, {name="length", value="255"}},
                body={}
            },
            {
                name="property",
                attributes={{name="name", value="email"}, {name="type", value="string"}, {name="length", value="255"}, {name="not-null", value="true"}},
                body={}
            },
            {
                name="property",
                attributes={{name="name", value="lastChange"}, {name="type", value="timestamp"}, {name="length", value="255"}, {name="not-null", value="true"}},
                body={}
            },
            {
                name="many-to-one",
                attributes={{name="name", value="list"}, {name="class", value="RecipientList"}, {name="column", value="list_id"}, {name="not-null", value="true"}},
                body=
                {
                    {
                        name="meta",
                        attributes={{name="attribute", value="finder"}},
                        body=
                        {
                            value="findByList"
                        }
                    },
                    {
                        name="meta",
                        attributes={{name="attribute", value="hibernate-type"}},
                        body=
                        {
                            value="entity"
                        }
                    }
                }
            }
        }
    }

